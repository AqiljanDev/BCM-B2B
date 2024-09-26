package kz.bcm.b2b.domain.repository

import kz.bcm.b2b.data.dto.wishlistAndCompare.wishlist.WishListFullDto
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
import kz.bcm.b2b.domain.data.order.findOne.FindOneOrderUser
import kz.bcm.b2b.domain.data.order.orders.OrderDetails
import kz.bcm.b2b.domain.data.order.orders.PostOrders
import kz.bcm.b2b.domain.data.wishlistAndCompare.GetMini
import kz.bcm.b2b.domain.data.wishlistAndCompare.wishlist.WishListFull

interface Repository {

    suspend fun findOne(
        category: String,
        min: Int?,
        max: Int?,
        sort: String,
        f: String,
        page: Int
    ): Catalog

    suspend fun collectCharacters(
        category: String,
        min: Int?,
        f: String
    ): CollectCharacters

    suspend fun search(
        value: String
    ): List<Product>

    suspend fun getCategories(): List<ChildCategory>




    suspend fun findOneProduct(slug: String): FindOneProduct


    suspend fun eventCompare(prodId: String): List<GetMini>
    suspend fun getMiniCompare(): List<GetMini>
    suspend fun getFullCompare(): CompareFull

    suspend fun eventFavorite(prodId: String): List<GetMini>
    suspend fun getMiniFavorite(): List<GetMini>
    suspend fun getFullFavorite(): List<WishListFull>


    suspend fun eventCart(item: PostCart): GetCartMini
    suspend fun getMiniCart(): GetCartMini
    suspend fun getFullCart(): GetCartFull
    suspend fun deleteCart(id: Int): GetCartMini


    suspend fun getBillMy(): List<BillMy>
    suspend fun createBill(body: BillBody): BillMy
    suspend fun updateBill(id: Int, body: BillBody): BillMy
    suspend fun deleteBill(id: Int)



    suspend fun postOrders(orders: PostOrders): OrderDetails
    suspend fun getMyOrder(): List<MyOrder>
    suspend fun getFindOneOrder(id: Int): kz.bcm.b2b.domain.data.order.findOne.FindOneProduct
    suspend fun getUserDiscount(): List<UserDiscount>


    suspend fun getCabinet(): Cabinet
    suspend fun updateCabinet(cabinet: Cabinet): Cabinet



    suspend fun login(body: PostLogin): AccessToken
    suspend fun registration(body: PostRegistration): AccessToken
    suspend fun passwordCodeSend(passwordSend: PasswordSend)
}
