package com.randoms.ai_assistant

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.randoms.ai_assistant.Repositories.ChatRepository
import com.randoms.ai_assistant.Repositories.CredentialRepository
import com.randoms.ai_assistant.Repositories.MessageRepository
import com.randoms.ai_assistant.base.BindAbleActivity
import com.randoms.ai_assistant.databinding.ActivityChatBinding
import com.randoms.ai_assistant.databinding.ActivityMessagesBinding
import com.randoms.ai_assistant.db.ChatDB
import com.randoms.ai_assistant.db.CredentialDB
import com.randoms.ai_assistant.entities.ChatEntity
import com.randoms.ai_assistant.entities.CredentialEntity
import com.randoms.ai_assistant.entities.MessageEntity
import com.randoms.ai_assistant.lib.Content
import com.randoms.ai_assistant.lib.ContentPartRequest
import com.randoms.ai_assistant.lib.ContentRequest
import com.randoms.ai_assistant.lib.GeminiAPIClient
import com.randoms.ai_assistant.lib.GenerateContentResponse
import com.randoms.ai_assistant.lib.Parts
import com.randoms.ai_assistant.lib.RequestBody
import com.randoms.ai_assistant.views.MessagesAdapter
import com.randoms.ai_assistant.views.OnMessageDeleteListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessagesActivity : BindAbleActivity<ActivityMessagesBinding>()
, OnMessageDeleteListener {
    override fun init(): ActivityMessagesBinding {
        val binding = ActivityMessagesBinding.inflate(layoutInflater);
        setContentView(binding.root)
        return binding
    }

    private lateinit var messagesAdapter: MessagesAdapter
    private lateinit var chatId: String

    private var credential: CredentialEntity? = null
    private var chat: ChatEntity? = null

    override fun start() {

        chatId = intent.getStringExtra("ChatKey") ?: "";

        setupChatRepository()
        setupMessageRepository()
        setupCredentialRepository()

        handleRouting()
        renderMessages()
        handleMessageInput()

        setLoading(false)
    }

    private lateinit var messageRepository: MessageRepository
    private lateinit var credentialRepository: CredentialRepository
    private lateinit var chatRepository: ChatRepository

    private fun setupMessageRepository() {
        messageRepository = MessageRepository(application)
    }

    private fun setupCredentialRepository() {
        val credentialDao = CredentialDB.getDatabase(application).credentialDao()
        credentialRepository = CredentialRepository(credentialDao)
        credentialRepository.credentials.observe(this) {
            credential = it[0]
        }
    }

    private fun setupChatRepository() {
        val chatDao = ChatDB.getDatabase(application).chatDao()
        chatRepository = ChatRepository(chatDao)
        chatRepository.getChatById(chatId).observe(this) {
            chat = it;
        }
    }

    private fun handleMessageInput() {
        binding!!.sendButton.setOnClickListener {
            val msg = binding!!.messageInput.text.toString().trim()

            if(msg.isEmpty()) return@setOnClickListener;


            lifecycleScope.launch {
                messageRepository.insert(
                    MessageEntity(
                        id = 0,
                        chat_id = chatId,
                        message = msg,
                        is_prompt = true
                    )
                )
            }
            makeRequest(msg)

            binding!!.messageInput.setText("");
        }
    }

    private fun renderMessages() {
        val thisContext = this;

        messageRepository.getMessagesForChat(chatId).observe(this) {
            messagesAdapter = MessagesAdapter(it)
            messagesAdapter.setOnMessageDeleteListener(thisContext)
            binding!!.messagesList.layoutManager = LinearLayoutManager(thisContext)
            binding!!.messagesList.adapter = messagesAdapter
            binding!!.messagesList.scrollToPosition(it.size - 1)
        }
    }

    private fun handleRouting() {
        binding!!.credentialsBtn.setOnClickListener {
            startActivity(Intent(this, CredentialActivity::class.java))
        }

        binding!!.homeBtn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

    private fun makeRequest(msg: String) {

        if(credential == null || chat == null) return;

        setLoading(true)

        val requestBody = RequestBody(
            contents = listOf(
                ContentRequest(parts = listOf(
                    ContentPartRequest( text = "${if(chat != null) "${chat!!.context}\n" else ""}$msg")
                ))
            )
        )

        val call: Call<GenerateContentResponse> = GeminiAPIClient.apiService.generateContent(
            apiKey = credential!!.gemini_api_key,
            requestBody = requestBody
        )

        call.enqueue(object : Callback<GenerateContentResponse> {
            override fun onResponse(
                call: Call<GenerateContentResponse>,
                                    response: Response<GenerateContentResponse>) {
                setLoading(false)
                if (response.isSuccessful) {
                    val generateContentResponse: GenerateContentResponse? = response.body()

                    if(generateContentResponse != null && generateContentResponse.candidates.isNotEmpty()) {
                        val candidate = generateContentResponse.candidates[0]
                        var resText = "";

                        candidate.content.parts.forEach {
                            resText += it.text;
                        }

                        lifecycleScope.launch {
                            withContext(Dispatchers.IO) {
                                messageRepository.insert(
                                    MessageEntity(
                                        id = 0,
                                        chat_id = chatId,
                                        message = resText,
                                        is_prompt = false
                                    )
                                )
                            }
                        }
                    }
                }
                else {
                    setLoading(false)
                    println("API Call failed with code: ${response.code()}")
                    println("Error Body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<GenerateContentResponse>, t: Throwable) {
                setLoading(false)
                println("API Call failed with exception: ${t.message}")
            }
        })
    }

    private var loading: Boolean = false
    private fun setLoading(state: Boolean) {
        loading = state
        binding!!.progressBar.visibility = if(state) View.VISIBLE else View.GONE;
        binding!!.sendButton.setImageResource(
            if(state) R.drawable.send_btn_loading
            else R.drawable.msg_send_btn
        )
    }

    override fun onMessageDelete(messageEntity: MessageEntity) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                messageRepository.delete(messageEntity)
            }
        }
    }
}




