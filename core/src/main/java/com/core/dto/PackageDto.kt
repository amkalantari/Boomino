package com.core.dto

import android.graphics.drawable.Drawable

data class PackageDto(
    var appname: String = "",
    var pname: String = "",
    var versionName: String = "",
    var versionCode: Int = 0,
    var icon: Drawable
)