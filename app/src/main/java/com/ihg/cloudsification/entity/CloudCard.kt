package com.ihg.cloudsification.entity

data class CloudCard(
    var id : Long = 0,
    var tag : String,
    val imageUri : String,
    val time : String,
    val location : String,
    var description : String
)