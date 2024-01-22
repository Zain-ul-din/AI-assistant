package com.randoms.ai_assistant.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MessageEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val chat_id: String,
    val message: String,
    val is_prompt: Boolean
)

