package com.friosteven.rosebedsheet_kmp.core.data.dto

import com.friosteven.rosebedsheet_kmp.core.constant.CoreConstant

data class ErrorResponse(
    var errorCode: Int = CoreConstant.GENERIC_ERROR_CODE,
    var exceptionMessage: String? = CoreConstant.GENERIC_ERROR_MESSAGE
)
