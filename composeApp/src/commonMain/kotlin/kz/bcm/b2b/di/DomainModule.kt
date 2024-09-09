package kz.bcm.b2b.di

import kz.bcm.b2b.domain.usecase.DeleteCartUseCase
import kz.bcm.b2b.domain.usecase.EventCartUseCase
import kz.bcm.b2b.domain.usecase.EventCompareUseCase
import kz.bcm.b2b.domain.usecase.EventFavoriteUseCase
import kz.bcm.b2b.domain.usecase.GetCartMiniUseCase
import kz.bcm.b2b.domain.usecase.GetCompareMiniUseCase
import kz.bcm.b2b.domain.usecase.GetFavoriteMiniUseCase
import kz.bcm.b2b.domain.usecase.GetFindOneUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {
        GetFindOneUseCase( get() )
    }



    factory {
        GetCompareMiniUseCase( get() )
    }

    factory {
        GetFavoriteMiniUseCase( get() )
    }

    factory {
        GetCartMiniUseCase( get() )
    }



    factory {
        EventCompareUseCase( get() )
    }

    factory {
        EventFavoriteUseCase( get() )
    }

    factory {
        EventCartUseCase( get() )
    }



    factory {
        DeleteCartUseCase( get() )
    }
}
