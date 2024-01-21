package com.randoms.ai_assistant.Repositories

import androidx.lifecycle.LiveData
import com.randoms.ai_assistant.daos.CredentialDao
import com.randoms.ai_assistant.entities.CredentialEntity

class CredentialRepository(private val credentialDao: CredentialDao) {
    val credentials: LiveData<List<CredentialEntity>> = credentialDao.getAll()

    suspend fun update(credential: CredentialEntity) {
        credentialDao.update(credential)
    }
}
