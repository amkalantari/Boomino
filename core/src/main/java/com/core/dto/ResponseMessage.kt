package com.core.dto

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * Created by aMiir on 2/4/21
 * Drunk, Fix Later
 */
@Parcelize
data class ResponseMessage(
        @Keep var key: String,
        @Keep var message: String
) :Parcelable