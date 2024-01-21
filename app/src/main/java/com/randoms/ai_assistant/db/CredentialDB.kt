package com.randoms.ai_assistant.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.randoms.ai_assistant.daos.CredentialDao
import com.randoms.ai_assistant.entities.CredentialEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [CredentialEntity::class], version = 1)
abstract class CredentialDB: RoomDatabase() {
    abstract fun credentialDao(): CredentialDao

    companion object {

        @Volatile
        private var INSTANCE: CredentialDB? = null;

        fun getDatabase(context: Context): CredentialDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CredentialDB::class.java,
                    "credential_db"
                ).fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                getDatabase(context).credentialDao().insert(
                                    CredentialEntity(
                                        id = 0,
                                        openai_api_key = "",
                                        gemini_api_key = ""
                                    )
                                )
                            }
                        }
                    })
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
