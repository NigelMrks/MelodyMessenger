package com.example.messenger.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.messenger.R
import com.example.messenger.data.model.Friend
import com.example.messenger.data.model.Message
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Repository {
    @RequiresApi(Build.VERSION_CODES.O)
    fun loadFriends(): MutableList<Friend> {
        return mutableListOf(
            Friend(
                1,
                R.drawable.eminem,
                "Eminem",
                testMessages()
            ),
            Friend(
                2,
                R.drawable.ariana,
                "Ariana Grande",
                mutableListOf()
            ),
            Friend(
                3,
                R.drawable.brunomars,
                "Bruno Mars",
                mutableListOf()
            ),
            Friend(
                4,
                R.drawable.edsheeran,
                "Ed Sheeran",
                mutableListOf()
            ),
            Friend(
                5,
                R.drawable.eltonjohn,
                "Elton John",
                mutableListOf()
            ),
            Friend(
                6,
                R.drawable.elvis,
                "Elvis",
                mutableListOf()
            ),
            Friend(
                7,
                R.drawable.lildicky,
                "Lil' Dicky",
                mutableListOf()
            ),
            Friend(
                8,
                R.drawable.snoopdogg,
                "Snoop Dogg",
                mutableListOf()
            ),
            Friend(
                9,
                R.drawable.tomcardy,
                "Tom Cardy",
                mutableListOf()
            ),
            Friend(
                10,
                R.drawable.usher,
                "Usher",
                mutableListOf()
            ),
        )
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun testMessages() : MutableList<Message> {
        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy")
        val formatted = current.format(formatter)

        val messageList = mutableListOf<Message>()

        var i = 0
        var isByUser: Boolean = true
        while (i < 10) {
            i++
            isByUser = i % 2 == 0
            messageList.add(
                0,
                Message(
                    "Hey this is test-message #$i",
                    formatted,
                    isByUser,
                    System.currentTimeMillis()
                )
            )
        }
        return messageList
    }
}