package com.example.messenger.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.messenger.ViewModel
import com.example.messenger.databinding.FragmentFriendslistBinding
import com.example.recyclerwiederholung_news.adapter.FriendAdapter

class FriendlistFragment : Fragment() {
    private lateinit var binding: FragmentFriendslistBinding
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendslistBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val friendAdapter = FriendAdapter()
        binding.friendRecycler.adapter = friendAdapter
        viewModel.friends.observe(viewLifecycleOwner){
            friendAdapter.submitList(viewModel.sortList(it))
        }
    }
}