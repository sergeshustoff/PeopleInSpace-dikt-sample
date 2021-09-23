package com.surrus.common.repository

import android.content.Context
import co.touchlab.kermit.LogcatLogger
import co.touchlab.kermit.Logger
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.surrus.common.di.PlatformModule
import com.surrus.peopleinspace.db.PeopleInSpaceDatabase

class PlatformModuleImpl(
    private val context: Context
) : PlatformModule {
    override val logger: Logger by lazy { LogcatLogger() }
    override val db: PeopleInSpaceDatabase by lazy {
        val driver =
            AndroidSqliteDriver(PeopleInSpaceDatabase.Schema, context, "peopleinspace.db")

        PeopleInSpaceDatabase(driver)
    }
}