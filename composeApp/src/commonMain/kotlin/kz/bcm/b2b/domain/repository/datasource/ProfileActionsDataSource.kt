package kz.bcm.b2b.domain.repository.datasource

import kz.bcm.b2b.data.dto.order.orders.OrderDetailsDto
import kz.bcm.b2b.domain.data.bill.BillBody
import kz.bcm.b2b.domain.data.bill.BillMy
import kz.bcm.b2b.domain.data.cabinet.Cabinet
import kz.bcm.b2b.domain.data.order.findMyOrder.MyOrder
import kz.bcm.b2b.domain.data.findOneCatalog.UserDiscount
import kz.bcm.b2b.domain.data.order.findOne.FindOneOrderUser
import kz.bcm.b2b.domain.data.order.findOne.FindOneProduct
import kz.bcm.b2b.domain.data.order.orders.OrderDetails
import kz.bcm.b2b.domain.data.order.orders.PostOrders

interface ProfileActionsDataSource {

    suspend fun getBillMy(): List<BillMy>
    suspend fun postBill(body: BillBody): BillMy
    suspend fun putBill(id: Int, body: BillBody): BillMy
    suspend fun delBill(id: Int)

    suspend fun postOrders(orders: PostOrders): OrderDetails

    suspend fun getMyOrders(): List<MyOrder>
    suspend fun getUserDiscount(): List<UserDiscount>
    suspend fun getFindOneOrder(id: Int): FindOneProduct

    suspend fun getCabinet(): Cabinet
    suspend fun updateCabinet(cabinet: Cabinet): Cabinet
}
