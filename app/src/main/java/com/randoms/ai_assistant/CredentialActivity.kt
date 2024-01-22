package com.randoms.ai_assistant

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.randoms.ai_assistant.Repositories.ChatRepository
import com.randoms.ai_assistant.Repositories.CredentialRepository
import com.randoms.ai_assistant.base.BindAbleActivity
import com.randoms.ai_assistant.databinding.ActivityCredentialBinding
import com.randoms.ai_assistant.db.ChatDB
import com.randoms.ai_assistant.db.CredentialDB
import com.randoms.ai_assistant.entities.CredentialEntity
import com.randoms.ai_assistant.util.UrlUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CredentialActivity : BindAbleActivity<ActivityCredentialBinding>() {
    override fun init(): ActivityCredentialBinding {
        val binding = ActivityCredentialBinding.inflate(layoutInflater);
        setContentView(binding.root)
        return binding;
    }

    private lateinit var credentialRepository: CredentialRepository

    override fun start() {
        handleInput()
        handleRouting()
        setupRepository()
        observe()
        handleLoadUrls()
    }

    private fun setupRepository() {
        val credentialDao = CredentialDB.getDatabase(application).credentialDao()
        credentialRepository = CredentialRepository(credentialDao)
    }

    private fun observe () {
        credentialRepository.credentials.observe(this) {
            val firstEntry = it[0]
            binding!!.geminiInputLayout.editText?.setText("*".repeat(firstEntry.gemini_api_key.length));
            binding!!.openaiInputLayout.editText?.setText("*".repeat(firstEntry.openai_api_key.length));
        }
    }

    private fun handleRouting() {
        binding!!.homeBtn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        binding!!.chatBtn.setOnClickListener {
            startActivity(Intent(this, ChatActivity::class.java));
        }
    }

    private fun handleInput() {

        binding!!.saveCredentialsBtn.setOnClickListener {

            val currentCredential = credentialRepository.credentials.value?.firstOrNull()

            val geminiKey = binding!!.geminiInputLayout.editText?.text.toString().trim()
            val openAiKey = binding!!.openaiInputLayout.editText?.text.toString().trim()

            val isValidKeyGemini = geminiKey.isNotEmpty() && !geminiKey.all {
                it == '*'
            }

            val isValidKeyOpenAI = openAiKey.isNotEmpty() && !openAiKey.all {
                it == '*'
            }

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    credentialRepository.update(
                        CredentialEntity(
                            id= 0,
                            gemini_api_key = if (isValidKeyGemini) geminiKey else currentCredential!!.gemini_api_key,
                            openai_api_key = if(isValidKeyOpenAI) openAiKey else currentCredential!!.openai_api_key
                        )
                    )
                }
            }
        }
    }

    private fun handleLoadUrls () {
        binding!!.getGeminiKey.setOnClickListener {
            UrlUtils.openUrl(this, "https://makersuite.google.com/app/apikey")
        }

        binding!!.getOpenAiKey.setOnClickListener {
            UrlUtils.openUrl(this, "https://platform.openai.com/account/api-keys")
        }
    }
}


