package kz.bcm.b2b.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.appendPathSegments
import kz.bcm.b2b.data.dto.bill.BillMyDto
import kz.bcm.b2b.data.dto.cabinet.CabinetDto
import kz.bcm.b2b.data.dto.order.findMyOrder.MyOrderDto
import kz.bcm.b2b.data.dto.findOneCatalog.UserDiscountDto
import kz.bcm.b2b.data.dto.order.findOne.FindOneOrderUserDto
import kz.bcm.b2b.data.dto.order.findOne.FindOneProductDto
import kz.bcm.b2b.data.dto.order.orders.OrderDetailsDto
import kz.bcm.b2b.di.ErrorResponse
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
import kz.bcm.b2b.presentation.other.ApiResponse
import kz.bcm.b2b.presentation.other.safeRequest

class ProfileActionsDataSourceImpl(
    private val httpClient: HttpClient
) : ProfileActionsDataSource {

    override suspend fun getBillMy(): List<BillMy> {

        val entry = httpClient.safeRequest<List<BillMyDto>, ErrorResponse> {
            method = HttpMethod.Get

            url {
                appendPathSegments("bills")
            }
        }

        return if (entry is ApiResponse.Success) entry.body else listOf()
    }

    override suspend fun postBill(body: BillBody): BillMy {

        val entry = httpClient.safeRequest<BillMyDto, ErrorResponse> {
            method = HttpMethod.Post

            url {
                appendPathSegments("bills")
            }
            setBody(body)
        }

        return if (entry is ApiResponse.Success) entry.body else BillMyDto()
    }

    override suspend fun putBill(id: Int, body: BillBody): BillMy {

        val entry = httpClient.safeRequest<BillMyDto, ErrorResponse> {
            method = HttpMethod.Put

            url {
                appendPathSegments("bills/$id")
            }
            setBody(body)
        }

        return if (entry is ApiResponse.Success) entry.body else BillMyDto()
    }

    override suspend fun delBill(id: Int) {
        httpClient.delete("bills/$id")
    }




    override suspend fun postOrders(orders: PostOrders): OrderDetails {

        val entry = httpClient.safeRequest<OrderDetailsDto, ErrorResponse> {
            method = HttpMethod.Post

            url {
                appendPathSegments("orders")
            }
            setBody(orders)
        }

        return if (entry is ApiResponse.Success) entry.body else OrderDetailsDto()
    }

    override suspend fun getMyOrders(): List<MyOrder> {

        val entry = httpClient.safeRequest<List<MyOrderDto>, ErrorResponse> {
            method = HttpMethod.Get

            url {
                appendPathSegments("orders/my")
            }
        }

        return if (entry is ApiResponse.Success) entry.body else listOf()
    }

    override suspend fun getUserDiscount(): List<UserDiscount> {

        val entry = httpClient.safeRequest<List<UserDiscountDto>, ErrorResponse> {
            method = HttpMethod.Get

            url {
                appendPathSegments("discount")
            }
        }

        return if (entry is ApiResponse.Success) entry.body else listOf()
    }


    override suspend fun getFindOneOrder(id: Int): FindOneProduct {

        val entry = httpClient.safeRequest<FindOneProductDto, ErrorResponse> {
            method = HttpMethod.Get

            url {
                appendPathSegments("orders/$id")
            }
        }

        return if (entry is ApiResponse.Success) entry.body else FindOneProductDto()
    }



    override suspend fun getCabinet(): Cabinet {

        val entry = httpClient.safeRequest<CabinetDto, ErrorResponse> {
            method = HttpMethod.Get

            url {
                appendPathSegments("cabinet")
            }
        }

        return if (entry is ApiResponse.Success) entry.body else CabinetDto()
    }

    override suspend fun updateCabinet(cabinet: Cabinet): Cabinet {

        val entry = httpClient.safeRequest<CabinetDto, ErrorResponse> {
            method = HttpMethod.Put

            url { appendPathSegments("cabinet") }
            setBody(cabinet)
        }

        return if (entry is ApiResponse.Success) entry.body else CabinetDto()
    }
}

