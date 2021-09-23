package com.surrus.peopleinspace

import android.app.Application
import com.surrus.common.di.CommonModule
import com.surrus.common.repository.PlatformModuleImpl
import com.surrus.peopleinspace.di.AppModule

class PeopleInSpaceApplication : Application() {

    val module by lazy {
        AppModule(CommonModule(PlatformModuleImpl(this)))
    }

    override fun onCreate() {
        super.onCreate()

        module.kermit().d { "PeopleInSpaceApplication" }
    }
}
