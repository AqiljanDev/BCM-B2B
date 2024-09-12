package kz.bcm.b2b.data.repository

import kz.bcm.b2b.domain.data.cart.event.PostCart
import kz.bcm.b2b.domain.data.cart.mini.GetCartMini
import kz.bcm.b2b.domain.data.collectCharacters.CollectCharacters
import kz.bcm.b2b.domain.data.findOneCatalog.Catalog
import kz.bcm.b2b.domain.data.findOneProduct.FindOneProduct
import kz.bcm.b2b.domain.data.wishlistAndCompare.GetMini
import kz.bcm.b2b.domain.repository.Repository
import kz.bcm.b2b.domain.repository.datasource.CatalogDataSource
import kz.bcm.b2b.domain.repository.datasource.ProductActionsDataSource
import kz.bcm.b2b.domain.repository.datasource.ProductsDataSource

class RepositoryImpl(
    private val catalogDataSource: CatalogDataSource,
    private val productsDataSource: ProductsDataSource,
    private val productActionsDataSource: ProductActionsDataSource
) : Repository {
    override suspend fun findOne(
        category: String,
        min: Int?,
        max: Int?,
        sort: String,
        f: String,
        page: Int
    ): Catalog {
        return catalogDataSource.findOne(category, min, max, sort, f, page)
    }

    override suspend fun collectCharacters(
        category: String,
        min: Int?,
        f: String
    ): CollectCharacters {
        return catalogDataSource.collectCharacters(category, min, f)
    }


    override suspend fun findOneProduct(slug: String): FindOneProduct {
        return productsDataSource.findOne(slug)
    }


    override suspend fun eventCompare(prodId: String): List<GetMini> {
        return productActionsDataSource.eventCompare(prodId)
    }

    override suspend fun getMiniCompare(): List<GetMini> {
        return productActionsDataSource.getMiniCompare()
    }


    override suspend fun eventFavorite(prodId: String): List<GetMini> {
        return productActionsDataSource.eventFavorite(prodId)
    }

    override suspend fun getMiniFavorite(): List<GetMini> {
        return productActionsDataSource.getMiniFavorite()
    }


    override suspend fun eventCart(item: PostCart): GetCartMini {
        return productActionsDataSource.eventCart(item)
    }

    override suspend fun getMiniCart(): GetCartMini {
        return productActionsDataSource.getMiniCart()
    }

    override suspend fun deleteCart(id: Int): GetCartMini {
        return productActionsDataSource.removeCart(id)
    }


}