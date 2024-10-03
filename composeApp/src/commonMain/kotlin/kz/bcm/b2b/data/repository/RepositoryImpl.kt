package kz.bcm.b2b.data.repository

import kz.bcm.b2b.domain.data.auth.AccessToken
import kz.bcm.b2b.domain.data.auth.login.PostLogin
import kz.bcm.b2b.domain.data.auth.passwordCodeSend.PasswordSend
import kz.bcm.b2b.domain.data.auth.register.PostRegistration
import kz.bcm.b2b.domain.data.bill.BillBody
import kz.bcm.b2b.domain.data.bill.BillMy
import kz.bcm.b2b.domain.data.cabinet.Cabinet
import kz.bcm.b2b.domain.data.cart.event.PostCart
import kz.bcm.b2b.domain.data.cart.full.GetCartFull
import kz.bcm.b2b.domain.data.cart.mini.GetCartMini
import kz.bcm.b2b.domain.data.categories.ChildCategory
import kz.bcm.b2b.domain.data.collectCharacters.CollectCharacters
import kz.bcm.b2b.domain.data.compare.CompareFull
import kz.bcm.b2b.domain.data.order.findMyOrder.MyOrder
import kz.bcm.b2b.domain.data.findOneCatalog.Catalog
import kz.bcm.b2b.domain.data.findOneCatalog.Product
import kz.bcm.b2b.domain.data.findOneCatalog.UserDiscount
import kz.bcm.b2b.domain.data.findOneProduct.FindOneProduct
import kz.bcm.b2b.domain.data.order.orders.OrderDetails
import kz.bcm.b2b.domain.data.order.orders.PostOrders
import kz.bcm.b2b.domain.data.sale.GetSaleAll
import kz.bcm.b2b.domain.data.sale.GetSaleOne
import kz.bcm.b2b.domain.data.wishlistAndCompare.GetMini
import kz.bcm.b2b.domain.data.wishlistAndCompare.wishlist.WishListFull
import kz.bcm.b2b.domain.repository.Repository
import kz.bcm.b2b.domain.repository.datasource.AuthDataSource
import kz.bcm.b2b.domain.repository.datasource.CatalogDataSource
import kz.bcm.b2b.domain.repository.datasource.ProductActionsDataSource
import kz.bcm.b2b.domain.repository.datasource.ProductsDataSource
import kz.bcm.b2b.domain.repository.datasource.ProfileActionsDataSource

class RepositoryImpl(
    private val catalogDataSource: CatalogDataSource,
    private val productsDataSource: ProductsDataSource,
    private val productActionsDataSource: ProductActionsDataSource,
    private val profileActionsDataSource: ProfileActionsDataSource,
    private val authDataSource: AuthDataSource
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

    override suspend fun search(value: String): List<Product> {
        return catalogDataSource.search(value)
    }

    override suspend fun getCategories(): List<ChildCategory> {
        return catalogDataSource.getCategories()
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

    override suspend fun getFullCompare(): CompareFull {
        return productActionsDataSource.getFullCompare()
    }


    override suspend fun eventFavorite(prodId: String): List<GetMini> {
        return productActionsDataSource.eventFavorite(prodId)
    }

    override suspend fun getMiniFavorite(): List<GetMini> {
        return productActionsDataSource.getMiniFavorite()
    }

    override suspend fun getFullFavorite(): List<WishListFull> {
        return productActionsDataSource.getFullFavorite()
    }



    override suspend fun eventCart(item: PostCart): GetCartMini {
        return productActionsDataSource.eventCart(item)
    }

    override suspend fun getMiniCart(): GetCartMini {
        return productActionsDataSource.getMiniCart()
    }

    override suspend fun getFullCart(): GetCartFull {
        return productActionsDataSource.getFullCart()
    }

    override suspend fun deleteCart(id: Int): GetCartMini {
        return productActionsDataSource.removeCart(id)
    }




    override suspend fun getBillMy(): List<BillMy> {
        return profileActionsDataSource.getBillMy()
    }

    override suspend fun createBill(body: BillBody): BillMy {
        return profileActionsDataSource.postBill(body)
    }

    override suspend fun updateBill(id: Int, body: BillBody): BillMy {
        return profileActionsDataSource.putBill(id, body)
    }

    override suspend fun deleteBill(id: Int) {
        profileActionsDataSource.delBill(id)
    }




    override suspend fun postOrders(orders: PostOrders): OrderDetails {
        return profileActionsDataSource.postOrders(orders)
    }

    override suspend fun getMyOrder(): List<MyOrder> {
        return profileActionsDataSource.getMyOrders()
    }

    override suspend fun getUserDiscount(): List<UserDiscount> {
        return profileActionsDataSource.getUserDiscount()
    }

    override suspend fun getFindOneOrder(id: Int): kz.bcm.b2b.domain.data.order.findOne.FindOneProduct {
        return profileActionsDataSource.getFindOneOrder(id)
    }



    override suspend fun getCabinet(): Cabinet {
        return profileActionsDataSource.getCabinet()
    }

    override suspend fun updateCabinet(cabinet: Cabinet): Cabinet {
        return profileActionsDataSource.updateCabinet(cabinet)
    }

    override suspend fun login(body: PostLogin): AccessToken {
        return authDataSource.login(body)
    }

    override suspend fun registration(body: PostRegistration): AccessToken {
        return authDataSource.registration(body)
    }

    override suspend fun passwordCodeSend(passwordSend: PasswordSend) {
        return authDataSource.passwordCodeSend(passwordSend)
    }



    override suspend fun findAllSale(): List<GetSaleAll> {
        return productActionsDataSource.findAllSale()
    }

    override suspend fun findOneSale(slug: String): GetSaleOne {
        return productActionsDataSource.findOneSale(slug)
    }

}