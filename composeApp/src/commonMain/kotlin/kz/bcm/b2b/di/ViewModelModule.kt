package kz.bcm.b2b.di

import kz.bcm.b2b.presentation.viewmodel.CatalogViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    single {
        CatalogViewModel(
            get(), get(),
            get(), get(),
            get(), get(),
            get(), get()
        )
    }
}