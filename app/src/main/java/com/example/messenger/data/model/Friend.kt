package com.example.messenger.data.model

data class Friend(
    val id: Int,
    val imageResource: Int,
    val name: String,
    val chatHistory: MutableList<Message>
)
