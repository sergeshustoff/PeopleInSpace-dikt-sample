package com.surrus.peopleinspace.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.surrus.peopleinspace.PeopleInSpaceApplication
import com.surrus.peopleinspace.di.AppModule

@Composable
inline fun <reified T : ViewModel> moduleViewModel(noinline create: AppModule.() -> T): T {
    val owner = LocalViewModelStoreOwner.current!!
    val module = LocalContext.current.appModule()

    return remember {
        ViewModelProvider(owner, LambdaFactory(module, create)).get()
    }
}

fun Context.appModule() = (applicationContext as PeopleInSpaceApplication).module

class LambdaFactory<T: ViewModel>(
    private val module: AppModule,
    private val create: AppModule.() -> T
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return create.invoke(module) as T
    }
}