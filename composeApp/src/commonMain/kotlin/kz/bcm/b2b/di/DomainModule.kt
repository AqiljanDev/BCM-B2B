package kz.bcm.b2b.di

import kz.bcm.b2b.domain.usecase.GetFindOneUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {
        GetFindOneUseCase( get() )
    }
}
