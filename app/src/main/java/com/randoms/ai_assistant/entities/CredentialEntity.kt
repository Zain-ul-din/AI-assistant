package com.randoms.ai_assistant.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "credentials_table")
data class CredentialEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Long = 0,
    val openai_api_key: String,
    val gemini_api_key: String
)



