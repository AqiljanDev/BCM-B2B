package kz.bcm.b2b.di

import kz.bcm.b2b.domain.usecase.DeleteCartUseCase
import kz.bcm.b2b.domain.usecase.EventCartUseCase
import kz.bcm.b2b.domain.usecase.EventCompareUseCase
import kz.bcm.b2b.domain.usecase.EventFavoriteUseCase
import kz.bcm.b2b.domain.usecase.GetBillMyUseCase
import kz.bcm.b2b.domain.usecase.GetCartFullUseCase
import kz.bcm.b2b.domain.usecase.GetCartMiniUseCase
import kz.bcm.b2b.domain.usecase.GetCollectCharactersUseCase
import kz.bcm.b2b.domain.usecase.GetCompareMiniUseCase
import kz.bcm.b2b.domain.usecase.GetFavoriteFullUseCase
import kz.bcm.b2b.domain.usecase.GetFavoriteMiniUseCase
import kz.bcm.b2b.domain.usecase.GetFindOneProductsUseCase
import kz.bcm.b2b.domain.usecase.GetFindOneUseCase
import kz.bcm.b2b.domain.usecase.PostOrdersUseCase
import org.koin.dsl.module

val domainModule = module {


    // Request catalog: findOne and collectCharacters
    factory {
        GetFindOneUseCase( get() )
    }

    factory {
        GetCollectCharactersUseCase( get() )
    }



    // Request product: findOne with slug
    factory {
        GetFindOneProductsUseCase( get() )
    }



    // Request product action: mini
    factory {
        GetCompareMiniUseCase( get() )
    }

    factory {
        GetFavoriteMiniUseCase( get() )
    }

    factory {
        GetCartMiniUseCase( get() )
    }



    // Request product action: full
    factory {
        GetCartFullUseCase( get() )
    }
    factory {
        GetFavoriteFullUseCase( get() )
    }



    // Request product action: event
    factory {
        EventCompareUseCase( get() )
    }

    factory {
        EventFavoriteUseCase( get() )
    }

    factory {
        EventCartUseCase( get() )
    }



    // Request product action: delete
    factory {
        DeleteCartUseCase( get() )
    }



    // Request profile action: billMy
    factory {
        GetBillMyUseCase( get() )
    }

    factory {
        PostOrdersUseCase( get() )
    }
}
