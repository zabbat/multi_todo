package net.wandroid.mytodo.features.app_bar.di

import net.wandroid.mytodo.features.app_bar.presentation.MenuAppBarViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appbarModule = module {
    viewModelOf(::MenuAppBarViewModel)
}