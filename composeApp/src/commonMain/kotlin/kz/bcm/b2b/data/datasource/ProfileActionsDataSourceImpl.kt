package kz.bcm.b2b.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import kz.bcm.b2b.data.dto.bill.BillMyDto
import kz.bcm.b2b.data.dto.cabinet.CabinetDto
import kz.bcm.b2b.data.dto.order.findMyOrder.MyOrderDto
import kz.bcm.b2b.data.dto.findOneCatalog.UserDiscountDto
import kz.bcm.b2b.data.dto.order.findOne.FindOneOrderUserDto
import kz.bcm.b2b.data.dto.order.findOne.FindOneProductDto
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
import kz.bcm.b2b.domain.repository.datasource.ProfileActionsDataSource

class ProfileActionsDataSourceImpl(
    private val httpClient: HttpClient
) : ProfileActionsDataSource {

    override suspend fun getBillMy(): List<BillMy> {
        val res: List<BillMyDto> = httpClient.get("bills").body()

        return res
    }

    override suspend fun postBill(body: BillBody): BillMy {
        val res: BillMyDto = httpClient.post("bills") {
            setBody(body)
        }.body()

        return res
    }

    override suspend fun putBill(id: Int, body: BillBody): BillMy {
        val res: BillMyDto = httpClient.put("bills/$id") {
            setBody(body)
        }.body()

        return res
    }

    override suspend fun delBill(id: Int) {
        httpClient.delete("bills/$id")
    }




    override suspend fun postOrders(orders: PostOrders): OrderDetails {
        val response: OrderDetailsDto = httpClient.post("orders") {
            setBody(orders)
        }.body()

        return response
    }

    override suspend fun getMyOrders(): List<MyOrder> {
        val res: List<MyOrderDto> = httpClient.get("orders/my").body()

        return res
    }

    override suspend fun getUserDiscount(): List<UserDiscount> {
        val res: List<UserDiscountDto> = httpClient.get("discount").body()

        return res
    }

    override suspend fun getFindOneOrder(id: Int): FindOneProduct {
        val res: FindOneProductDto = httpClient.get("orders/$id").body()

        return res
    }



    override suspend fun getCabinet(): Cabinet {
        val res: CabinetDto = httpClient.get("cabinet").body()

        return res
    }

    override suspend fun updateCabinet(cabinet: Cabinet): Cabinet {
        val res: CabinetDto = httpClient.put("cabinet") {

            setBody(cabinet)
        }.body()

        return res
    }
}

