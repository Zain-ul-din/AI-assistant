package com.randoms.ai_assistant.Repositories

import androidx.lifecycle.LiveData
import com.randoms.ai_assistant.daos.ChatDao
import com.randoms.ai_assistant.entities.ChatEntity

class ChatRepository (private val chatDao: ChatDao) {

    val allChat: LiveData<List<ChatEntity>> = chatDao.getAll()

    suspend fun insert(chat: ChatEntity) {
        chatDao.insert(chat)
    }

    suspend fun delete(chat: ChatEntity) {
        chatDao.delete(chat)
    }

    suspend fun update(chat: ChatEntity) {
        chatDao.update(chat)
    }
}
