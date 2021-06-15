package com.amiir.boomino.util.bindingAdapter

operator fun StringBuilder.plus(text: String?) {
    append(text)
}