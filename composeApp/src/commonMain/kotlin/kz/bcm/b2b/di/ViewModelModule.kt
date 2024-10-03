package kz.bcm.b2b.di

import kz.bcm.b2b.presentation.ui.compare.CompareScreen
import kz.bcm.b2b.presentation.viewmodel.CardViewModel
import kz.bcm.b2b.presentation.viewmodel.CartViewModel
import kz.bcm.b2b.presentation.viewmodel.CatalogViewModel
import kz.bcm.b2b.presentation.viewmodel.CompareViewModel
import kz.bcm.b2b.presentation.viewmodel.FavoriteViewModel
import kz.bcm.b2b.presentation.viewmodel.ProfileViewModel
import kz.bcm.b2b.presentation.ui.splash.SplashViewModel
import kz.bcm.b2b.presentation.viewmodel.CatalogListViewModel
import kz.bcm.b2b.presentation.viewmodel.FilterFullViewModel
import kz.bcm.b2b.presentation.viewmodel.FilterViewModel
import kz.bcm.b2b.presentation.viewmodel.LoginViewModel
import kz.bcm.b2b.presentation.viewmodel.PromotionFullViewModel
import kz.bcm.b2b.presentation.viewmodel.PromotionViewModel
import kz.bcm.b2b.presentation.viewmodel.RegistrationViewModel
import kz.bcm.b2b.presentation.viewmodel.RestoreCodeViewModel
import kz.bcm.b2b.presentation.viewmodel.SearchViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import kotlin.math.sin

val viewModelModule = module {

    single {
        CatalogViewModel(
            get(), get(),
            get(), get(),
            get(), get(),
            get(), get(),
            get(), get()
        )
    }

    single {
        CardViewModel(
            get(), get(),
            get(), get(),
            get(), get(),
            get(), get(),
            get()
        )
    }

    single {
        CartViewModel(
            get(), get(),
            get(), get(),
            get(), get()
        )
    }

    single {
        FavoriteViewModel(
            get(),get(),
            get(),get(),
            get(),get(),
            get(),get(),
            get()
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

    single {
        SplashViewModel( get() )
    }

    single {
        LoginViewModel(
            get()
        )
    }
    single {
        RegistrationViewModel(
            get()
        )
    }
    single {
        RestoreCodeViewModel(
            get()
        )
    }

    single {
        SearchViewModel(
            get(), get(),
            get(), get(),
            get(), get(),
            get(), get(),
            get(), get()
        )
    }

    single {
        FilterViewModel(
            get(), get(),
            get(), get(),
            get(), get(),
            get(), get(),
            get(), get()
        )
    }

    single {
        FilterFullViewModel(
            get(), get()
        )
    }

    single {
        CatalogListViewModel(
            get()
        )
    }

    single {
        PromotionViewModel(
            get()
        )
    }

    single {
        PromotionFullViewModel(
            get()
        )
    }
}
