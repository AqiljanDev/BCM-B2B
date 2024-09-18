package kz.bcm.b2b.di

import kz.bcm.b2b.presentation.ui.compare.CompareScreen
import kz.bcm.b2b.presentation.viewmodel.CardViewModel
import kz.bcm.b2b.presentation.viewmodel.CartViewModel
import kz.bcm.b2b.presentation.viewmodel.CatalogViewModel
import kz.bcm.b2b.presentation.viewmodel.CompareViewModel
import kz.bcm.b2b.presentation.viewmodel.FavoriteViewModel
import kz.bcm.b2b.presentation.viewmodel.ProfileViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    single {
        CatalogViewModel(
            get(), get(),
            get(), get(),
            get(), get(),
            get(), get(),
            get()
        )
    }

    single {
        CardViewModel(
            get(), get(),
            get(), get(),
            get(), get(),
            get(), get()
        )
    }

    single {
        CartViewModel(
            get(), get(),
            get(), get(),
            get()
        )
    }

    single {
        FavoriteViewModel(
            get(),get(),
            get(),get(),
            get(),get(),
            get(),get()
        )
    }

    single {
        ProfileViewModel(
            get(), get(),
            get(), get(),
            get(), get(),
            get(), get(),
            get(), get(),
            get(), get()
        )
    }

    single {
        CompareViewModel(
            get(), get(),
            get(), get(),
            get(), get()
        )
    }
}