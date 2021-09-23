package com.surrus.common.repository

import co.touchlab.kermit.Logger
import co.touchlab.kermit.NSLogLogger
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.surrus.common.di.PlatformModule
import com.surrus.peopleinspace.db.PeopleInSpaceDatabase

class PlatformModuleImpl : PlatformModule {
    override val logger: Logger by lazy { NSLogLogger() }
    override val db: PeopleInSpaceDatabase by lazy {
        val driver = NativeSqliteDriver(PeopleInSpaceDatabase.Schema, "peopleinspace.db")
        PeopleInSpaceDatabase(driver)
    }
}