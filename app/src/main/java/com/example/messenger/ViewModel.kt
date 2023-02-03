package com.example.messenger

import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.data.Repository
import com.example.messenger.data.model.Friend
import com.example.messenger.data.model.Message
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Timer
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule

@RequiresApi(Build.VERSION_CODES.O)
class ViewModel : ViewModel() {

    private val repository = Repository()

    private var _friends = MutableLiveData<MutableList<Friend>>()
    val friends: LiveData<MutableList<Friend>>
        get() = _friends

    private var _chatHistory = MutableLiveData<MutableList<Message>>()
    val chatHistory: LiveData<MutableList<Message>>
        get() = _chatHistory

    init {
        _friends.value = repository.loadFriends()
    }

    fun getChatHistory(id: Int){
        var friendsList = _friends.value
        if (friendsList != null) {
            if (friendsList.isNotEmpty()) {
                var friend = friendsList.find { it.id == id }
                if (friend != null) {
                    _chatHistory.value = friend.chatHistory
                }
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage(message: String, time: String, current: Long) {
        Log.d("ViewModel", "sendMessage function is called")
        Log.d("ViewModel", "Message: $message; Time: $time")
        _chatHistory.value?.add(
            0,
            Message(
                message,
                time,
                true,
                current
            )
        )
        Log.d("ViewModel", "Message is added to _chatHistory")
        _chatHistory.value = _chatHistory.value
        viewModelScope.launch {
            delay(2000)
            reply()
        }
    }
    private fun reply() {
        val listOfReplies = listOf<String>(
            "Hey I'm busy right now",
            "I'm at work, message me later",
            "Sorry I have to finish writing this song",
        )
        val message = listOfReplies.random()
        val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy"))
        val current = System.currentTimeMillis()
        _chatHistory.value?.add(
            0,
            Message(
                message,
                time,
                false,
                current
            )
        )
        _chatHistory.value = _chatHistory.value
    }

    fun sortList(list: MutableList<Friend>) : List<Friend> {
        var listWithChatHistory = mutableListOf<Friend>()
        var listWithoutChatHistory = mutableListOf<Friend>()
        var fullList = mutableListOf<Friend>()

        for (friend in list) {
            if (friend.chatHistory.isNotEmpty()) listWithChatHistory.add(friend)
            else listWithoutChatHistory.add(friend)
        }

        fullList.addAll(
            listWithChatHistory.sortedByDescending { it.chatHistory.first().time }
        )
        fullList.addAll(
            listWithoutChatHistory.sortedBy { it.name }
        )

        return fullList
    }
}