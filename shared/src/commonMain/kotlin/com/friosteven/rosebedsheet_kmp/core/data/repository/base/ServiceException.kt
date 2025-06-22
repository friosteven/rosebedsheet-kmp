package com.friosteven.rosebedsheet_kmp.core.data.repository.base

import com.friosteven.rosebedsheet_kmp.core.data.dto.ErrorResponse


class ServiceException(val error: ErrorResponse) : RuntimeException(error.exceptionMessage)