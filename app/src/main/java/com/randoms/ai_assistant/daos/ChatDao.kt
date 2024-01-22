package com.randoms.ai_assistant.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.randoms.ai_assistant.entities.ChatEntity

@Dao
interface ChatDao {
    @Insert
    fun insert(chat: ChatEntity)

    @Update
    fun update(chat: ChatEntity)

    @Delete
    fun delete(chat: ChatEntity)

    @Query("delete from chat_table")
    fun deleteAll()

    @Query("select * from chat_table")
    fun getAll(): LiveData<List<ChatEntity>>

    @Query("SELECT * FROM chat_table WHERE title = :id")
    fun getChatById(id: String): LiveData<ChatEntity>
}
