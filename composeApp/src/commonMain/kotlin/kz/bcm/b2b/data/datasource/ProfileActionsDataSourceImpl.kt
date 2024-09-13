package kz.bcm.b2b.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kz.bcm.b2b.data.dto.bill.BillMyDto
import kz.bcm.b2b.data.dto.orders.OrderDetailsDto
import kz.bcm.b2b.domain.data.bill.BillMy
import kz.bcm.b2b.domain.data.orders.OrderDetails
import kz.bcm.b2b.domain.data.orders.PostOrders
import kz.bcm.b2b.domain.repository.datasource.ProfileActionsDataSource

class ProfileActionsDataSourceImpl(
    private val httpClient: HttpClient
) : ProfileActionsDataSource {

    override suspend fun getBillMy(): List<BillMy> {
        val res: List<BillMyDto> = httpClient.get("bills").body()

        return res
    }

    override suspend fun postOrders(orders: PostOrders): OrderDetails {
        val response: OrderDetailsDto = httpClient.post("orders") {
            setBody(orders)
        }.body()

        return response
    }
}