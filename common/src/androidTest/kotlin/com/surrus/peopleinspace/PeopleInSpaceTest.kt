package com.surrus.peopleinspace

import com.surrus.common.di.CommonModule
import com.surrus.common.repository.PlatformModuleImpl
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class PeopleInSpaceTest {
    @Test
    fun testGetPeople() = runBlocking {
        val module = CommonModule(PlatformModuleImpl(mockk()), enableNetworkLogs = true)
        val peopleInSpaceApi = module.api()
        val result = peopleInSpaceApi.fetchPeople()
        println(result)
        assertTrue(result.people.isNotEmpty())
    }
}
