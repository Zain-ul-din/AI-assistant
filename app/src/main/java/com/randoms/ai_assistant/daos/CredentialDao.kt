package com.randoms.ai_assistant.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.randoms.ai_assistant.entities.CredentialEntity

@Dao
interface CredentialDao {

    @Insert
    fun insert(credentialEntity: CredentialEntity)

    @Update
    fun update(credentialEntity: CredentialEntity)

    @Query("select * from credentials_table")
    fun getAll(): LiveData<List<CredentialEntity>>

}
