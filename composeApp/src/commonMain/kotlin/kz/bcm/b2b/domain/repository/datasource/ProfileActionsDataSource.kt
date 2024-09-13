package kz.bcm.b2b.domain.repository.datasource

import kz.bcm.b2b.data.dto.orders.OrderDetailsDto
import kz.bcm.b2b.domain.data.bill.BillMy
import kz.bcm.b2b.domain.data.orders.OrderDetails
import kz.bcm.b2b.domain.data.orders.PostOrders

interface ProfileActionsDataSource {

    suspend fun getBillMy(): List<BillMy>

    suspend fun postOrders(orders: PostOrders): OrderDetails
}
