package kz.bcm.b2b.di

import kz.bcm.b2b.domain.usecase.DeleteBillUseCase
import kz.bcm.b2b.domain.usecase.DeleteCartUseCase
import kz.bcm.b2b.domain.usecase.EventCartUseCase
import kz.bcm.b2b.domain.usecase.EventCompareUseCase
import kz.bcm.b2b.domain.usecase.EventFavoriteUseCase
import kz.bcm.b2b.domain.usecase.GetBillMyUseCase
import kz.bcm.b2b.domain.usecase.GetCabinetUseCase
import kz.bcm.b2b.domain.usecase.GetCartFullUseCase
import kz.bcm.b2b.domain.usecase.GetCartMiniUseCase
import kz.bcm.b2b.domain.usecase.GetCategoriesUseCase
import kz.bcm.b2b.domain.usecase.GetCollectCharactersUseCase
import kz.bcm.b2b.domain.usecase.GetCompareFullUseCase
import kz.bcm.b2b.domain.usecase.GetCompareMiniUseCase
import kz.bcm.b2b.domain.usecase.GetFavoriteFullUseCase
import kz.bcm.b2b.domain.usecase.GetFavoriteMiniUseCase
import kz.bcm.b2b.domain.usecase.GetFindOneOrderUseCase
import kz.bcm.b2b.domain.usecase.GetFindOneProductsUseCase
import kz.bcm.b2b.domain.usecase.GetFindOneUseCase
import kz.bcm.b2b.domain.usecase.GetMyOrdersUseCase
import kz.bcm.b2b.domain.usecase.GetSaleOneUseCase
import kz.bcm.b2b.domain.usecase.GetSaleUseCase
import kz.bcm.b2b.domain.usecase.GetSearchUseCase
import kz.bcm.b2b.domain.usecase.GetUserDiscountUseCase
import kz.bcm.b2b.domain.usecase.PostBillUseCase
import kz.bcm.b2b.domain.usecase.PostLoginUseCase
import kz.bcm.b2b.domain.usecase.PostOrdersUseCase
import kz.bcm.b2b.domain.usecase.PostPasswordCodeSendUseCase
import kz.bcm.b2b.domain.usecase.PostRegistrationUseCase
import kz.bcm.b2b.domain.usecase.PutBillUseCase
import kz.bcm.b2b.domain.usecase.PutCabinetUseCase
import org.koin.dsl.module

val domainModule = module {


    // Request catalog: findOne and collectCharacters
    factory {
        GetFindOneUseCase( get() )
    }

    factory {
        GetCollectCharactersUseCase( get() )
    }

    factory {
        GetSearchUseCase( get() )
    }

    factory {
        GetCategoriesUseCase( get() )
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
    factory {
        GetCompareFullUseCase( get() )
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
        PostBillUseCase( get() )
    }

    factory {
        PutBillUseCase( get() )
    }

    factory {
        DeleteBillUseCase( get() )
    }



    // Request profile action: order
    factory {
        PostOrdersUseCase( get() )
    }

    factory {
        GetMyOrdersUseCase( get() )
    }

    factory {
        GetFindOneOrderUseCase( get() )
    }


    // Request profile action: discount
    factory {
        GetUserDiscountUseCase( get() )
    }


    // Request profile action: Cabinet
    factory {
        GetCabinetUseCase( get() )
    }

    factory {
        PutCabinetUseCase( get() )
    }


    // Auth
    factory {
        PostLoginUseCase( get() )
    }

    factory {
        PostRegistrationUseCase( get() )
    }

    factory {
        PostPasswordCodeSendUseCase( get() )
    }



    // Sale
    factory {
        GetSaleUseCase( get() )
    }
    factory {
        GetSaleOneUseCase( get() )
    }
}
