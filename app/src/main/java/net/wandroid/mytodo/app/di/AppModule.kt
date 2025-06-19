package net.wandroid.mytodo.app.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import net.wandroid.mytodo.app.config.AppConfigProvider
import net.wandroid.mytodo.app.presentation.MainViewModel
import net.wandroid.mytodo.features.app_bar.di.appbarModule
import net.wandroid.mytodo.features.auth.di.authModule
import net.wandroid.mytodo.features.config.ConfigProvider
import net.wandroid.mytodo.features.todo.list.di.todoModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val appModule = module {
    single<CoroutineScope> {
        CoroutineScope(Dispatchers.Main + SupervisorJob())
    }
    single<ConfigProvider> { AppConfigProvider(androidContext()) }
    viewModelOf(::MainViewModel)
}

internal val appModules = module {
    includes(
        appModule,
        todoModule,
        authModule,
        appbarModule,
    )
}