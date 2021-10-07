package com.surrus.common.di

import co.touchlab.kermit.Kermit
import com.surrus.common.remote.PeopleInSpaceApi
import com.surrus.common.repository.PeopleInSpaceRepository
import com.surrus.common.repository.PeopleInSpaceRepositoryInterface
import dev.shustoff.dikt.CreateSingle
import dev.shustoff.dikt.UseModules
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.serialization.json.Json

@UseModules(PlatformModule::class)
class CommonModule(
    private val platformModule: PlatformModule,
    private val enableNetworkLogs: Boolean = false
) {
    private val json: Json by lazy {
        Json { isLenient = true; ignoreUnknownKeys = true }
    }

    private val httpClient by lazy {
        createHttpClient(json, enableNetworkLogs)
    }

    val kermit by lazy {
        Kermit(platformModule.logger)
    }

    @CreateSingle fun api(): PeopleInSpaceApi

    @CreateSingle fun repoImpl(): PeopleInSpaceRepository

    fun repo(): PeopleInSpaceRepositoryInterface = repoImpl()

    companion object {

        private fun createHttpClient(json: Json, enableNetworkLogs: Boolean) = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(json)
            }
            if (enableNetworkLogs) {
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.INFO
                }
            }
        }
    }
}
