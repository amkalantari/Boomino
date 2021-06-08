package com.amiir.radchart.util.bindingAdapter

operator fun StringBuilder.plus(text: String?) {
    append(text)
}