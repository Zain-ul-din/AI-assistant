package com.randoms.ai_assistant.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.randoms.ai_assistant.entities.MessageEntity

@Dao
interface MessageDao {
    @Insert()
    suspend fun insertMessage(messageEntity: MessageEntity)

    @Delete
    suspend fun delete(messageEntity: MessageEntity)

    @Query("SELECT * FROM message_table WHERE chat_id = :chatId ORDER BY id ASC")
    fun getMessagesForChat(chatId: String): LiveData<List<MessageEntity>>
}



