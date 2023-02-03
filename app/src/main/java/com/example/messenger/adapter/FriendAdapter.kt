package com.example.recyclerwiederholung_news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.R
import com.example.messenger.data.model.Friend
import com.example.messenger.ui.FriendlistFragment
import com.example.messenger.ui.FriendlistFragmentDirections
import java.time.LocalDateTime

class FriendAdapter() : RecyclerView.Adapter<FriendAdapter.ItemViewHolder>() {

    private var dataset = listOf<Friend>()

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.friendlist_name_text)
        val lastMessage: TextView = view.findViewById(R.id.friendlist_lastmessage_text)
        val image: ImageView = view.findViewById(R.id.friendlist_friend_profile_image)
        val card: CardView = view.findViewById(R.id.friendlist_friend_card)
        val timeSince: TextView = view.findViewById(R.id.friendlist_time_since)
    }

    fun submitList(list: List<Friend>) {
        dataset = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.friend_item, parent, false)

        return ItemViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val friend: Friend = dataset[position]

        holder.name.text = friend.name
        if (friend.chatHistory.isNotEmpty()){
            holder.lastMessage.text = friend.chatHistory.first().content

            var passedTimeString: String = "test"
            var millisecondsSince: Long = System.currentTimeMillis()
            millisecondsSince -= friend.chatHistory.first().time
            var minutesSince: Int = (millisecondsSince / 60000).toInt()
            when (minutesSince) {
                0 -> passedTimeString = "<1 min ago"
                1 -> passedTimeString = "1 min ago"
                in (2..59) -> passedTimeString = "$minutesSince mins ago"
                in (60..1440) -> {
                    passedTimeString = if (minutesSince%60 != 0) "${minutesSince/60}h ${minutesSince%60}m ago"
                    else "${minutesSince/60} hours"
                }
                in (1440..Int.MAX_VALUE) -> "over a day ago"

            }
            holder.timeSince.text = passedTimeString
        }
        holder.image.setImageResource(friend.imageResource)





        holder.card.setOnClickListener {
            holder.itemView.findNavController()
                .navigate(FriendlistFragmentDirections.actionFriendlistFragmentToChatFragment(friend.id))
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}
