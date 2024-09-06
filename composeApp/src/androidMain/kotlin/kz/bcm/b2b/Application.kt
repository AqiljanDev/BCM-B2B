package kz.bcm.b2b

import android.app.Application
import kz.bcm.b2b.di.dataModule
import kz.bcm.b2b.di.domainModule
import kz.bcm.b2b.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            androidLogger()
            modules(viewModelModule, domainModule, dataModule)
        }
    }
}

