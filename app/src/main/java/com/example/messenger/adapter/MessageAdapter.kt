package com.example.recyclerwiederholung_news.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.R
import com.example.messenger.data.model.Message
import com.google.android.material.card.MaterialCardView


class MessageAdapter() : RecyclerView.Adapter<MessageAdapter.ItemViewHolder>() {

    private var dataset = mutableListOf<Message>()

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messageContent = view.findViewById<TextView>(R.id.chat_message_text)
        val messageDate = view.findViewById<TextView>(R.id.chat_message_date)
        val messageCard = view.findViewById<MaterialCardView>(R.id.chat_message_card)
        val layout = view.findViewById<ConstraintLayout>(R.id.chat_constraint_layout)
        val layout2 = view.findViewById<ConstraintLayout>(R.id.chat_constraint_in_card)
    }

    fun submitList(list: MutableList<Message>) {
        dataset = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_item, parent, false)

        return ItemViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val message: Message = dataset[position]

        holder.messageContent.text = message.content
        holder.messageDate.text = message.dateAndTime

        val constraintSet1 = ConstraintSet()
        constraintSet1.clone(holder.layout)
        constraintSet1.setHorizontalBias(R.id.chat_message_card, 1.0F)
        val constraintSet3 = ConstraintSet()
        constraintSet3.clone(holder.layout2)
        constraintSet3.setHorizontalBias(R.id.chat_message_date, 1.0F)
        val constraintSet2 = ConstraintSet()
        constraintSet2.clone(holder.layout)
        constraintSet2.setHorizontalBias(R.id.chat_message_card, 0.0F)
        val constraintSet4 = ConstraintSet()
        constraintSet4.clone(holder.layout2)
        constraintSet4.setHorizontalBias(R.id.chat_message_date, 0.0F)

        if (message.isByUser) {
            constraintSet1.applyTo(holder.layout)
            constraintSet3.applyTo(holder.layout2)
            holder.messageCard.setCardBackgroundColor(Color.parseColor("#000000"))
            holder.messageCard.strokeColor = Color.parseColor("#1C461E")
        }
        else {
            constraintSet2.applyTo(holder.layout)
            constraintSet4.applyTo(holder.layout2)
            holder.messageCard.setCardBackgroundColor(Color.parseColor("#1C461E"))
            holder.messageCard.strokeColor = Color.parseColor("#000000")


        }
        //val constraint = if (message.isByUser) constraintSet2 else constraintSet1
        //constraint.applyTo(holder.layout)

        /** val params: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )

        if (message.isByUser) {
            params.setMargins(128, 8, 16, 8)
            holder.messageCard.layoutParams = params
        }
        else {
            params.setMargins(16, 8, 128, 8)
            holder.messageCard.layoutParams = params
        } */
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}
