package com.randoms.ai_assistant

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.randoms.ai_assistant.Repositories.ChatRepository
import com.randoms.ai_assistant.base.BindAbleActivity
import com.randoms.ai_assistant.databinding.ActivityChatBinding
import com.randoms.ai_assistant.db.ChatDB
import com.randoms.ai_assistant.entities.ChatEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatActivity: BindAbleActivity<ActivityChatBinding>() {
    override fun init(): ActivityChatBinding {
        val binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        return binding;
    }

    override fun start() {
        setupRepository()
        handleRouting()
        handleForm()
    }

    private fun handleRouting() {

        binding!!.homeBtn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        binding!!.credentialsBtn.setOnClickListener {
            startActivity(Intent(this, CredentialActivity::class.java))
        }
    }

    private lateinit var chatRepository: ChatRepository

    private fun setupRepository() {
        val chatDao = ChatDB.getDatabase(application).chatDao()
        chatRepository = ChatRepository(chatDao)
    }

    private fun handleForm () {
        binding!!.submitBtn.setOnClickListener {
            val title = binding!!.editTextTitle.text.toString().trim()
            val chatContext = binding!!.editTextContext.text.toString().trim()

            if(title.isEmpty()) return@setOnClickListener;

            val newChat = ChatEntity(title = title, context = chatContext)


            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    chatRepository.insert(newChat)
                }
            }

            startActivity(Intent(this, HomeActivity::class.java));
        }
    }
}
