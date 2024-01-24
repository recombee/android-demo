package com.recombee.android_app_demo

fun <X, R> memoize(fn: (X) -> R): (X) -> R {
    val cache: MutableMap<X, R> = HashMap()
    return {
        cache.getOrPut(it) { fn(it) }
    }
}
