package com.core.dto

import androidx.annotation.Keep

@Keep
class ResultDto<T> {
    @Keep
    var code = 0

    @Keep
    var message: String? = null

    @Keep
    var data: T? = null
}

@Keep
data class ResultMessage<T>(
    @Keep var success: Boolean = false,
    @Keep var responseMessages: List<ResponseMessage>? = null,
    @Keep val page: Int = 0,
    @Keep val pageSize: Int = 0,
    @Keep val totalCount: Int = 0,
    @Keep val totalPage: Int = 0,
    @Keep val items: T? = null
)

@Keep
data class ResultGeneralMessage(
    @Keep var success: Boolean = false,
    @Keep var responseMessages: List<ResponseMessage>? = null
)

