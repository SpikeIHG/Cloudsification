package com.ihg.cloudsification.entity


data class Badge(
    val name: String,
    val description: String,
    val drawableId: Int,
    var isUnlocked: Boolean = false
)
