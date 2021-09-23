package com.surrus.common.repository

import co.touchlab.kermit.CommonLogger
import co.touchlab.kermit.Logger
import com.surrus.common.di.PlatformModule
import com.surrus.peopleinspace.db.PeopleInSpaceDatabase

class PlatformModuleImpl : PlatformModule {
    override val logger: Logger by lazy { CommonLogger() }
    override val db: PeopleInSpaceDatabase? = null
}