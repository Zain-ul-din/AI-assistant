package com.randoms.ai_assistant

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.randoms.ai_assistant.Repositories.ChatRepository
import com.randoms.ai_assistant.base.BindAbleActivity
import com.randoms.ai_assistant.databinding.ActivityHomeBinding
import com.randoms.ai_assistant.db.ChatDB
import com.randoms.ai_assistant.entities.ChatEntity
import com.randoms.ai_assistant.views.ChatAdapter
import com.randoms.ai_assistant.views.OnChatClickListener
import com.randoms.ai_assistant.views.OnChatDeleteClickListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : BindAbleActivity<ActivityHomeBinding>(),
    OnChatDeleteClickListener, OnChatClickListener {

    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chatRepository: ChatRepository

    override fun init(): ActivityHomeBinding {
        val binding = ActivityHomeBinding.inflate(layoutInflater);
        setContentView(binding.root)
        return binding
    }

    override fun start() {
        handleRouting()
        setupRepository()
        initRecyclerView()
    }

    private fun setupRepository() {
        val chatDao = ChatDB.getDatabase(application).chatDao()
        chatRepository = ChatRepository(chatDao)
    }

    private fun initRecyclerView() {
        val thisContext = this;
        chatRepository.allChat.observe(this) {
            chatAdapter = ChatAdapter(it)
            chatAdapter.setOnChatDeleteClickListener(thisContext)
            chatAdapter.setOnChatClickListener(thisContext)
            binding?.chatView?.layoutManager = LinearLayoutManager(this)
            binding?.chatView?.adapter = chatAdapter
        }
    }

    private fun handleRouting() {
        binding!!.homeBtn.setOnClickListener {
            startActivity(Intent(this, ChatActivity::class.java));
        }

        binding!!.credentialsBtn.setOnClickListener {
            startActivity(Intent(this, CredentialActivity::class.java));
        }
    }

    override fun onChatDeleteClick(ele: ChatEntity) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                chatRepository.delete(ele)
            }
        }
    }

    override fun onChatClick(element: ChatEntity) {
        val intent = Intent(this, MessagesActivity::class.java);
        intent.putExtra("ChatKey", element.title);
        startActivity(intent);
    }
}

