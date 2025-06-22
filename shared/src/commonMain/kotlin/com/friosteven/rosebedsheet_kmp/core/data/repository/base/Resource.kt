package com.friosteven.rosebedsheet_kmp.core.data.repository.base

data class Resource<out T>(
    val status: Status,
    val code: Int?,
    var details: String? = "",
    var hint: String? = "",
    val data: T?,
    var message: String?,
    val exception: ServiceException?
) {
    companion object {
        fun <T> success(data: T?, code: Int? = null, message: String? = null): Resource<T> =
            Resource(status = Status.SUCCESS, code = code, data = data, message = message, exception = null)

        fun <T> error(data: T?, code: Int? = null, message: String, exception: ServiceException): Resource<T> =
            Resource(status = Status.ERROR, code = code, data = data, message = message, exception = exception)

        fun <T> loading(data: T? = null): Resource<T> =
            Resource(status = Status.LOADING, code = null, data = data, message = null, exception = null)
    }
}
