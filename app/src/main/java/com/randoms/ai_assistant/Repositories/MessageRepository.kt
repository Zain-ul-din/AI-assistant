package com.randoms.ai_assistant.Repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.randoms.ai_assistant.daos.MessageDao
import com.randoms.ai_assistant.db.MessagesDB
import com.randoms.ai_assistant.entities.MessageEntity

class MessageRepository(application: Application) {

    private val messageDao: MessageDao

    init {
        val database = MessagesDB.getDatabase(application)
        messageDao = database.messageDao()
    }

    suspend fun insert(message: MessageEntity) {
        messageDao.insertMessage(message)
    }

    suspend fun delete(message: MessageEntity) {
        messageDao.delete(message)
    }

    fun getMessagesForChat(chatId: String): LiveData<List<MessageEntity>> {
        return messageDao.getMessagesForChat(chatId)
    }
}

