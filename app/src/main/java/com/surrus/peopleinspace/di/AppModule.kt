package com.surrus.peopleinspace.di

import co.touchlab.kermit.Kermit
import com.surrus.common.di.CommonModule
import com.surrus.peopleinspace.ui.PeopleInSpaceViewModel
import dev.shustoff.dikt.Create
import dev.shustoff.dikt.Provide
import dev.shustoff.dikt.UseModules

@UseModules(CommonModule::class)
class AppModule(
    private val commonModule: CommonModule
) {
    @Create fun createVM(): PeopleInSpaceViewModel
    @Provide fun kermit(): Kermit
}
