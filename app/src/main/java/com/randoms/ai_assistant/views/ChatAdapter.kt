package com.randoms.ai_assistant.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.randoms.ai_assistant.entities.ChatEntity
import com.randoms.ai_assistant.R

interface OnChatDeleteClickListener {
    fun onChatDeleteClick(element: ChatEntity)
}

class ChatAdapter (private val list: List<ChatEntity>):
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    class  ViewHolder(val view: View): RecyclerView.ViewHolder(view)

    private var onChatDeleteClickListener: OnChatDeleteClickListener? = null

    fun setOnChatDeleteClickListener(listener: OnChatDeleteClickListener) {
        this.onChatDeleteClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ChatAdapter.ViewHolder(
            LayoutInflater.from((parent.context)).inflate(R.layout.chat_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.count();
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chatEntity = list[position]
        val view = holder.view

        view.findViewById<ImageView>(R.id.chat_bg).setImageResource(R.drawable.chat_bg)

        view.findViewById<ImageButton>(R.id.chat_delete_btn).setOnClickListener {
            onChatDeleteClickListener?.onChatDeleteClick(chatEntity)
        }

        val headingTextView = view.findViewById<TextView>(R.id.heading)
        val descriptionTextView = view.findViewById<TextView>(R.id.description)

        headingTextView.text = chatEntity.title
        descriptionTextView.text = chatEntity.context
    }

}

