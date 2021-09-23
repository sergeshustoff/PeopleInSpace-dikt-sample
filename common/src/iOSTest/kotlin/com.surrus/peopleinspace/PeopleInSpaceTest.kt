package com.surrus.peopleinspace

import com.surrus.common.di.CommonModule
import com.surrus.common.repository.PlatformModuleImpl
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue

class PeopleInSpaceTest {
    @Test
    fun testGetPeople() = runBlocking {
        val module = CommonModule(PlatformModuleImpl(), enableNetworkLogs = true)
        val repo = module.repo()
        val result = repo.fetchPeople()
        println(result)
        assertTrue(result.isNotEmpty())
    }
}
