package com.recombee.android_app_demo.data

import com.recombee.apiclientkotlin.bindings.Recommendation

data class Item(
    val itemId: String,
    val title: String,
    val description: String,
    val images: List<String>,
) {
    constructor(
        recommendation: Recommendation
    ) : this(
        itemId = recommendation.id,
        title = recommendation.getValues()["title"] as? String ?: "",
        description = recommendation.getValues()["description"] as? String ?: "",
        images =
            (recommendation.getValues()["images"] as? List<*>)?.filterIsInstance<String>()
                ?: listOf(),
    )
}
