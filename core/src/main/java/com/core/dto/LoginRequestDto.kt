package com.core.dto

import androidx.annotation.Keep

@Keep
data class LoginRequestDto(
    @Keep var username: String,
    @Keep var password: String
)