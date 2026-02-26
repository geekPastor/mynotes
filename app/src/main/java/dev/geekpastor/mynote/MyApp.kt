package dev.geekpastor.mynote

import android.app.Application
import dev.geekpastor.mynote.data.di.dataModule
import dev.geekpastor.mynote.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp)
            modules(
                domainModule,
                dataModule,
                appModule
            )
        }
    }
}