package com.example.messenger.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.messenger.ViewModel
import com.example.messenger.data.model.Message
import com.example.messenger.databinding.FragmentChatBinding
import com.example.recyclerwiederholung_news.adapter.MessageAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val friendId = requireArguments().getInt("friendID")

        viewModel.friends.observe(viewLifecycleOwner) {list ->
            val friend = list.find { it.id == friendId }

            if (friend != null) {
                binding.chatNameText.text = friend.name
                binding.chatInputText.hint = "Message ${friend.name}..."
            }
        }

        val messageAdapter = MessageAdapter()
        binding.messageRecycler.adapter = messageAdapter
        viewModel.getChatHistory(friendId)
        viewModel.chatHistory.observe(viewLifecycleOwner){
            messageAdapter.submitList(it)
        }

        binding.chatSendButton.setOnClickListener{
            Log.d("ChatFragment", "Button clicked")
            if (binding.chatMessageInput.text != null) {
                Log.d("ChatFragment", "chatMessageInput != null")
                val message: String = binding.chatMessageInput.text.toString()
                val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy"))
                viewModel.sendMessage(message, time, System.currentTimeMillis())
                binding.chatMessageInput.setText("")
            }
        }
    }
}