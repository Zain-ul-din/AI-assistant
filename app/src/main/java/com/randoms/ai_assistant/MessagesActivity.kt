package com.randoms.ai_assistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.randoms.ai_assistant.base.BindAbleActivity
import com.randoms.ai_assistant.databinding.ActivityChatBinding

class MessagesActivity : BindAbleActivity<ActivityChatBinding>() {
    override fun init(): ActivityChatBinding {
        val binding = ActivityChatBinding.inflate(layoutInflater);
        setContentView(binding.root)
        return binding
    }
}


