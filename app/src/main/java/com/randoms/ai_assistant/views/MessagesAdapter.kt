package com.randoms.ai_assistant.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.randoms.ai_assistant.entities.MessageEntity
import com.randoms.ai_assistant.R


interface OnMessageDeleteListener {
    fun onMessageDelete(messageEntity: MessageEntity)
}

class MessagesAdapter(private val list: List<MessageEntity>):
    RecyclerView.Adapter<MessagesAdapter.ViewHolder>() {

    private var onMessageDeleteListener: OnMessageDeleteListener? = null

    fun setOnMessageDeleteListener (listener: OnMessageDeleteListener) {
        onMessageDeleteListener = listener
    }

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
        val deleteBtn = view.findViewById<ImageButton>(R.id.msg_delete_btn)

        deleteBtn.setOnClickListener {
            onMessageDeleteListener?.onMessageDelete(messageEntity)
        }

        message.setBackgroundResource(
            if(messageEntity.is_prompt) R.drawable.rounded_corner_bg_prompt_msg
            else R.drawable.rounded_corner_background
        );

        message.text = messageEntity.message
    }
}

