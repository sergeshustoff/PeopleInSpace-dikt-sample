package com.surrus.common.repository

import co.touchlab.kermit.CommonLogger
import co.touchlab.kermit.Logger
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import com.surrus.common.di.PlatformModule
import com.surrus.peopleinspace.db.PeopleInSpaceDatabase

class PlatformModuleImpl : PlatformModule {
    override val logger: Logger by lazy { CommonLogger() }
    override val db: PeopleInSpaceDatabase by lazy {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
            .also { PeopleInSpaceDatabase.Schema.create(it) }
        PeopleInSpaceDatabase(driver)
    }
}