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
import androidx.recyclerview.widget.LinearLayoutManager
import com.randoms.ai_assistant.base.BindAbleActivity
import com.randoms.ai_assistant.databinding.ActivityChatBinding
import com.randoms.ai_assistant.databinding.ActivityMessagesBinding
import com.randoms.ai_assistant.entities.MessageEntity
import com.randoms.ai_assistant.lib.Content
import com.randoms.ai_assistant.lib.ContentPartRequest
import com.randoms.ai_assistant.lib.ContentRequest
import com.randoms.ai_assistant.lib.GeminiAPIClient
import com.randoms.ai_assistant.lib.GenerateContentResponse
import com.randoms.ai_assistant.lib.Parts
import com.randoms.ai_assistant.lib.RequestBody
import com.randoms.ai_assistant.views.MessagesAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessagesActivity : BindAbleActivity<ActivityMessagesBinding>() {
    override fun init(): ActivityMessagesBinding {
        val binding = ActivityMessagesBinding.inflate(layoutInflater);
        setContentView(binding.root)
        return binding
    }

    private lateinit var messagesAdapter: MessagesAdapter

    override fun start() {
        handleRouting()
        renderMessages()
        setupKeyboardVisibilityListener()
    }

    private fun renderMessages() {
        messagesAdapter = MessagesAdapter(
            listOf(
                MessageEntity(
                    id = 0,
                    chat_id = "foo",
                    message = "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.\n" +
                            "\n" +
                            "5\n" +
                            "\tparagraphs\n" +
                            "\twords\n" +
                            "\tbytes\n" +
                            "\tlists\n" +
                            "\tStart with 'Lorem\n" +
                            "ipsum dolor sit amet...'\n",
                    is_prompt = false
                ),
                MessageEntity(
                    id = 0,
                    chat_id = "foo",
                    message = "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.\n" +
                            "\n" +
                            "5\n" +
                            "\tparagraphs\n" +
                            "\twords\n" +
                            "\tbytes\n" +
                            "\tlists\n" +
                            "\tStart with 'Lorem\n" +
                            "ipsum dolor sit amet...'\n",
                    is_prompt = false
                ),
                MessageEntity(
                    id = 0,
                    chat_id = "foo",
                    message = "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.\n" +
                            "\n" +
                            "5\n" +
                            "\tparagraphs\n" +
                            "\twords\n" +
                            "\tbytes\n" +
                            "\tlists\n" +
                            "\tStart with 'Lorem\n" +
                            "ipsum dolor sit amet...'\n",
                    is_prompt = false
                ),
                MessageEntity(
                    id = 0,
                    chat_id = "foo",
                    message = "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.\n" +
                            "\n" +
                            "5\n" +
                            "\tparagraphs\n" +
                            "\twords\n" +
                            "\tbytes\n" +
                            "\tlists\n" +
                            "\tStart with 'Lorem\n" +
                            "ipsum dolor sit amet...'\n",
                    is_prompt = false
                ),
                MessageEntity(
                    id = 0,
                    chat_id = "foo",
                    message = "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.\n" +
                            "\n" +
                            "5\n" +
                            "\tparagraphs\n" +
                            "\twords\n" +
                            "\tbytes\n" +
                            "\tlists\n" +
                            "\tStart with 'Lorem\n" +
                            "ipsum dolor sit amet...'\n",
                    is_prompt = false
                )
            )
        )
        binding!!.messagesList.layoutManager = LinearLayoutManager(this)
        binding!!.messagesList.adapter = messagesAdapter
    }

    private fun handleRouting() {
        binding!!.credentialsBtn.setOnClickListener {
            startActivity(Intent(this, CredentialActivity::class.java))
        }

        binding!!.homeBtn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

    private fun setupKeyboardVisibilityListener() {
        val rootView = findViewById<View>(android.R.id.content)
        rootView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            @RequiresApi(Build.VERSION_CODES.R)
            override fun onPreDraw(): Boolean {
                val insets = rootView.rootWindowInsets
                val isKeyboardVisible = insets.isVisible(WindowInsets.Type.systemBars())

                if (isKeyboardVisible) {
                    adjustRecyclerViewForKeyboard(true)
                } else {
                    adjustRecyclerViewForKeyboard(false)
                }

                return true
            }
        })
    }

    private fun adjustRecyclerViewForKeyboard(isKeyboardVisible: Boolean) {
        val params = binding!!.messagesList.layoutParams as androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
        val marginInDp = if (isKeyboardVisible) {
            200
        } else {
            0
        }
        params.setMargins(marginInDp, 0, marginInDp, 0)
        binding!!.messagesList.layoutParams = params
    }

    fun callMeLater() {

//        binding!!.testApi.setOnClickListener {
            // Define your API key and request body
            val apiKey = "AIzaSyC7j5Gy35tTNKhJhfIMLYu-jvuDgEMkM1A"
            val requestBody = RequestBody(
                contents = listOf(
                    ContentRequest(parts = listOf(
                        ContentPartRequest( text = "Write a story about a magic backpack")
                    ))
                )
            )

            // Make the API call
            val call: Call<GenerateContentResponse> = GeminiAPIClient.apiService.generateContent(
                apiKey = apiKey,
                requestBody = requestBody
            )

            call.enqueue(object : Callback<GenerateContentResponse> {
                override fun onResponse(call: Call<GenerateContentResponse>, response: Response<GenerateContentResponse>) {
                    if (response.isSuccessful) {
                        val generateContentResponse: GenerateContentResponse? = response.body()


//                        binding!!.textView2.text = response.body().toString()
                        println(response.body().toString())

                        if(generateContentResponse != null && generateContentResponse.candidates.isNotEmpty()) {
                            val candidate = generateContentResponse.candidates[0]
                            var resText = "";

                            candidate.content.parts.forEach {
                                resText += it.text;
                            }

//                            binding!!.textView2.text = resText
                        }
                    }
                    else {
                        println("API Call failed with code: ${response.code()}")
                        println("Error Body: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<GenerateContentResponse>, t: Throwable) {
                    // Log the failure
                    println("API Call failed with exception: ${t.message}")
                }
            })
//        }

        val key: String? = intent.getStringExtra("ChatKey");
    }
}




