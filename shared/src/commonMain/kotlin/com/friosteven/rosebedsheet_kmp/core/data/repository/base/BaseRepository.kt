package com.friosteven.rosebedsheet_kmp.core.data.repository.base

import com.friosteven.rosebedsheet_kmp.core.constant.CoreConstant
import com.friosteven.rosebedsheet_kmp.core.data.dto.BaseResponse
import com.friosteven.rosebedsheet_kmp.core.data.dto.ErrorResponse
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException


inline fun <T> serviceCall(callFunction: () -> T): Resource<T> = try {
    Resource.success(callFunction())
} catch (e: Exception) {
    catchResourceException(e)
}

inline fun <T> apiCall(callFunction: () -> BaseResponse<T>): Resource<T> = try {
    val response: BaseResponse<T> = callFunction()
    Resource.success(response.data, response.code, response.message)
} catch (e: Exception) {
    catchResourceException(e)
}

fun <T> catchResourceException(e: Exception): Resource<T> {
    e.printStackTrace()

    val exception = ServiceException(
        when (e) {
            is RedirectResponseException -> {
                ErrorResponse(
                    e.response.status.value,
                    e.response.status.description
                )
            }
            is ClientRequestException -> {
                ErrorResponse(
                    e.response.status.value,
                    e.response.status.description
                )
            }
            is ServerResponseException -> {
                ErrorResponse(
                    e.response.status.value,
                    e.response.status.description
                )
            }
            else -> {
                ErrorResponse(
                    exceptionMessage = e.message
                )
            }
        }
    )

    return Resource.error(
        null,
        null,
        exception.error.exceptionMessage ?: CoreConstant.GENERIC_ERROR_MESSAGE,
        exception
    )
}