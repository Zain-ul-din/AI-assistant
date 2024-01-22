package com.randoms.ai_assistant.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.randoms.ai_assistant.daos.MessageDao
import com.randoms.ai_assistant.entities.MessageEntity

@Database(entities = [MessageEntity::class], version = 1, exportSchema = false)
abstract class MessagesDB : RoomDatabase() {

    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var INSTANCE: MessagesDB? = null

        fun getDatabase(context: Context): MessagesDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MessagesDB::class.java,
                    "messages_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
