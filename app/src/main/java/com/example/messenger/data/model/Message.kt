package com.example.messenger.data.model

data class Message(
    var content: String,
    val dateAndTime: String,
    val isByUser: Boolean,
    val time: Long
)
