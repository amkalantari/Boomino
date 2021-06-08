package com.amiir.radchart.util.bindingAdapter

import android.content.res.Resources


fun Resources.pixelsToSp(px: Float): Float = px / displayMetrics.scaledDensity