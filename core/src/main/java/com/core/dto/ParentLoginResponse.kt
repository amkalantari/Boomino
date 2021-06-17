package com.core.dto

import androidx.annotation.Keep

@Keep
data class ParentLoginResponse(
    @Keep var token: String
)