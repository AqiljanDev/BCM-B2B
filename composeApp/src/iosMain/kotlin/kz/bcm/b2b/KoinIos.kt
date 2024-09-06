package kz.bcm.b2b

import kz.bcm.b2b.di.dataModule
import kz.bcm.b2b.di.domainModule
import kz.bcm.b2b.di.viewModelModule
import org.koin.core.context.startKoin

fun initKoinIos() {
    startKoin {
        modules(viewModelModule, domainModule, dataModule)
    }
}