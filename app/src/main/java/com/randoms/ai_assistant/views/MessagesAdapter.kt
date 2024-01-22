package com.randoms.ai_assistant.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.randoms.ai_assistant.entities.MessageEntity
import com.randoms.ai_assistant.R

class MessagesAdapter(private val list: List<MessageEntity>):
    RecyclerView.Adapter<MessagesAdapter.ViewHolder>() {

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return MessagesAdapter.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_message, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val messageEntity: MessageEntity = list[position]
        val view = holder.view

        val message = view.findViewById<TextView>(R.id.messageText)

        message.text = messageEntity.message
    }
}

