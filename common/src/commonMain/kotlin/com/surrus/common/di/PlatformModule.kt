package com.surrus.common.di

import co.touchlab.kermit.Logger
import com.surrus.peopleinspace.db.PeopleInSpaceDatabase

interface PlatformModule {
    val logger: Logger
    val db: PeopleInSpaceDatabase?
}