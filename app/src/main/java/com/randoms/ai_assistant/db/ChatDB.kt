package com.randoms.ai_assistant.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.randoms.ai_assistant.daos.ChatDao
import com.randoms.ai_assistant.entities.ChatEntity

@Database(entities = [ChatEntity::class], version = 2)
abstract class ChatDB: RoomDatabase() {

    abstract fun chatDao(): ChatDao

    companion object {

        @Volatile
        private var INSTANCE: ChatDB? = null;

        fun getDatabase(context: Context): ChatDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChatDB::class.java,
                    "db_name"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}