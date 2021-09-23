package com.surrus.peopleinspace

import com.surrus.common.di.CommonModule
import com.surrus.common.repository.PlatformModuleImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class PeopleInSpaceTest {
    @Test
    fun testGetPeople() = runBlocking {
        //TODO: mock context?
        val module = CommonModule(PlatformModuleImpl(TODO()), enableNetworkLogs = true)
        val peopleInSpaceApi = module.api()
        val result = peopleInSpaceApi.fetchPeople()
        println(result)
        assertTrue(result.people.isNotEmpty())
    }
}
