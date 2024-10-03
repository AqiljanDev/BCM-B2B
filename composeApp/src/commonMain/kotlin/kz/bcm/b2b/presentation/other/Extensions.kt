package kz.bcm.b2b.presentation.other

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException
import kz.bcm.b2b.data.dto.findOneCatalog.ParentCategoryDto
import kz.bcm.b2b.data.dto.findOneCatalog.ProductDto
import kz.bcm.b2b.domain.data.findOneCatalog.ParentCategory
import kz.bcm.b2b.domain.data.findOneCatalog.PriceDiscount
import kz.bcm.b2b.domain.data.findOneCatalog.Product
import kz.bcm.b2b.domain.data.findOneCatalog.UserCategory
import kz.bcm.b2b.domain.data.findOneCatalog.UserDiscount
import kz.bcm.b2b.domain.data.findOneProduct.FindOneProduct


fun <T> List<T>.toggleItem(item: T): List<T> {
    val newList = this.toMutableList()
    if (this.contains(item)) {
        newList.remove(item)
    } else {
        newList.add(item)
    }

    return newList.toList()
}



suspend inline fun <reified T, reified E> HttpClient.safeRequest(
    block: HttpRequestBuilder.() -> Unit,
): ApiResponse<T, E> =
    try {
        val response = request { block() }
        val sss = response.body<T>()

        println("safeRequest = success: $sss")
        ApiResponse.Success(sss)
    } catch (e: ClientRequestException) {

        println("safeRequest = ClientRequestException: ${e.message}")
        ApiResponse.Error.HttpError(e.response.status.value, e.errorBody())
    } catch (e: ServerResponseException) {

        println("safeRequest = ServerResponseException: ${e.message}")
        ApiResponse.Error.HttpError(e.response.status.value, e.errorBody())
    } catch (e: IOException) {

        println("safeRequest = IOException: ${e.message}")
        ApiResponse.Error.NetworkError
    } catch (e: SerializationException) {

        println("safeRequest = SerializationException: ${e.message}")
        ApiResponse.Error.SerializationError
    }


suspend inline fun <reified E> ResponseException.errorBody(): E? =
    try {

        response.body()
    } catch (e: SerializationException) {
        println("safeRequest -> errorBody = SerializationException: ${e.message}")
        null
    }


sealed class ApiResponse<out T, out E> {
    /**
     * Represents successful network responses (2xx).
     */
    data class Success<T>(val body: T) : ApiResponse<T, Nothing>()

    sealed class Error<E> : ApiResponse<Nothing, E>() {
        /**
         * Represents server (50x) and client (40x) errors.
         */
        data class HttpError<E>(val code: Int, val errorBody: E?) : Error<E>()

        /**
         * Represent IOExceptions and connectivity issues.
         */
        object NetworkError : Error<Nothing>()

        /**
         * Represent SerializationExceptions.
         */
        object SerializationError : Error<Nothing>()
    }
}


fun Product.discountPrice(listProfileDiscount: List<UserDiscount>): PriceDiscount {
    val product = this

    println("discountPrice = product: $product, listDiscount: $listProfileDiscount")

    // PRODUCT
    if (product.discount != null) {

        val discount = product.discount!!

        val price = if (discount.type == 1) {
            product.price - ((product.price * discount.value) / 100)
        } else product.price - discount.value

        println("discountPrice -> product = price: $price, type: ${discount.type}, value: ${discount.value}")
        return PriceDiscount(
            price,
            discount.type,
            discount.value
        )
    }

    // CATEGORY AND PARENT CATEGORY
    var discountCategory = product.categories.discount

    if (discountCategory == null) {
        val discountParentCat = product.categories.parentCategory

        fun findDiscountInCategory(category: ParentCategory?) {
            var currentCategory = category
            while (currentCategory != null) {
                if (currentCategory.discount != null) {
                    discountCategory = currentCategory.discount
                    return
                }
                currentCategory = currentCategory.parentCategory
            }
        }
        if (discountParentCat != null) findDiscountInCategory(discountParentCat)

    }

    println("discountPrice -> category = discount category: ${discountCategory}")

    discountCategory?.let {

        val price = if (it.type == 1) {
            product.price - ((product.price * it.value) / 100)
        } else product.price - it.value

        println("discountPrice -> category = price: $price, type: ${discount?.type}, value: ${discount?.value}")
        return PriceDiscount(
            price,
            it.type,
            it.value
        )
    }


    // USER MANY DISCOUNTS
    if (listProfileDiscount.isNotEmpty()) {

        var discount: UserDiscount? = null

        listProfileDiscount.forEach {
            val discountCats = it.userCategory

            val catIds = mutableListOf<String>()

            fun discountCatsFunc(cat: UserCategory) {
                catIds.add(cat.id1c)

                if (cat.childCategory != null) {

                    cat.childCategory!!.forEach {
                        if (!catIds.contains(it.id1c)) {
                            catIds.add(it.id1c)
                        }

                        if (it.childCategory != null) {
                            discountCatsFunc(it)
                        }
                    }
                }
            }

            if (discountCats != null) discountCatsFunc(discountCats)

            if (catIds.contains(product.categoriesId)) {
                discount = it
            }
        }

        println("discountPrice -> user = discount: $discount")

        discount?.let {

            val price = if (it.type == 1) {
                product.price - ((product.price * it.value) / 100)
            } else product.price - it.value

            println("discountCats -> user = price: $price, type: ${it.type}, value: ${it.value}\"")
            return PriceDiscount(
                price,
                it.type,
                it.value
            )
        }
    }

    println("discountCats not discounts")
    // NOT DISCOUNTS
    return PriceDiscount(
        price = product.price,
        discountType = 0,
        discountValue = 0
    )
}