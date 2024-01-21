package com.randoms.ai_assistant.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_table")
data class ChatEntity(
    @PrimaryKey
    val title: String,
    val context: String
)

