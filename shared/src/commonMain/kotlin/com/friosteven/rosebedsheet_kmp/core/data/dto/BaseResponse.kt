package com.friosteven.rosebedsheet_kmp.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<out T>(
    val code: Int,
    val message: String?,
    val data: T? = null,
    val pageNum: Int? = 0,
    val total: Int? = 0
)
