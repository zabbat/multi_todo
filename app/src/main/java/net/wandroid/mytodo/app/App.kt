package net.wandroid.mytodo.app

import android.app.Application
import net.wandroid.mytodo.app.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                appModules
            )
        }
    }
}