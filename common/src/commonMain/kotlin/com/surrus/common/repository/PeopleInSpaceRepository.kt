package com.surrus.common.repository

import co.touchlab.kermit.Kermit
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.surrus.common.remote.Assignment
import com.surrus.common.remote.IssPosition
import com.surrus.common.remote.PeopleInSpaceApi
import com.surrus.peopleinspace.db.PeopleInSpaceDatabase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext


interface PeopleInSpaceRepositoryInterface {
    fun fetchPeopleAsFlow(): Flow<List<Assignment>>
    fun pollISSPosition(): Flow<IssPosition>
    suspend fun fetchPeople(): List<Assignment>
}

class PeopleInSpaceRepository(
    private val peopleInSpaceApi: PeopleInSpaceApi,
    private val logger: Kermit,
    peopleInSpaceDatabase: PeopleInSpaceDatabase?
) : PeopleInSpaceRepositoryInterface {

    private val coroutineScope: CoroutineScope = MainScope()
    private val peopleInSpaceQueries = peopleInSpaceDatabase?.peopleInSpaceQueries

    var peopleJob: Job? = null

    init {
        coroutineScope.launch {
            fetchAndStorePeople()
        }
    }

    override fun fetchPeopleAsFlow(): Flow<List<Assignment>> {
        // the main reason we need to do this check is that sqldelight isn't currently
        // setup for javascript client
        return peopleInSpaceQueries?.selectAll(
            mapper = { name, craft, personImageUrl, personBio ->
                Assignment(name = name, craft = craft, personImageUrl = personImageUrl, personBio = personBio)
            }
        )?.asFlow()?.mapToList() ?: flowOf(emptyList<Assignment>())
    }

    private suspend fun fetchAndStorePeople() {
        logger.d { "fetchAndStorePeople" }
        val result = peopleInSpaceApi.fetchPeople()

        // this is very basic implementation for now that removes all existing rows
        // in db and then inserts results from api request
        // using "transaction" accelerate the batch of queries, especially inserting
        peopleInSpaceQueries?.transaction {
            peopleInSpaceQueries.deleteAll()
            result.people.forEach {
                peopleInSpaceQueries.insertItem(it.name, it.craft, it.personImageUrl, it.personBio)
            }
        }
    }

    // Used by web client atm
    override suspend fun fetchPeople() = peopleInSpaceApi.fetchPeople().people

    // called from Kotlin/Native clients
    fun startObservingPeopleUpdates(success: (List<Assignment>) -> Unit) {
        logger.d { "startObservingPeopleUpdates" }
        peopleJob = coroutineScope.launch {
            fetchPeopleAsFlow().collect {
                success(it)
            }
        }
    }

    fun stopObservingPeopleUpdates() {
        logger.d { "stopObservingPeopleUpdates, peopleJob = $peopleJob" }
        peopleJob?.cancel()
    }

    override fun pollISSPosition(): Flow<IssPosition> = flow {
        while (true) {
            val position = peopleInSpaceApi.fetchISSPosition().iss_position
            emit(position)
            logger.d("PeopleInSpaceRepository") { position.toString() }
            delay(POLL_INTERVAL)
        }
    }

    val iosScope: CoroutineScope = object : CoroutineScope {
        override val coroutineContext: CoroutineContext
            get() = SupervisorJob() + Dispatchers.Main
    }

    fun iosPollISSPosition() = KotlinNativeFlowWrapper<IssPosition>(pollISSPosition())

    companion object {
        private const val POLL_INTERVAL = 10000L
    }
}

class KotlinNativeFlowWrapper<T>(private val flow: Flow<T>) {
    fun subscribe(
        scope: CoroutineScope,
        onEach: (item: T) -> Unit,
        onComplete: () -> Unit,
        onThrow: (error: Throwable) -> Unit
    ) = flow
        .onEach { onEach(it) }
        .catch { onThrow(it) }
        .onCompletion { onComplete() }
        .launchIn(scope)
}
