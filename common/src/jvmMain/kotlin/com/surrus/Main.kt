package com.surrus

import com.surrus.common.di.CommonModule
import com.surrus.common.repository.PlatformModuleImpl
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val module = CommonModule(PlatformModuleImpl(), enableNetworkLogs = true)
        val api = module.api()
        println(api.fetchPeople())
    }
}
