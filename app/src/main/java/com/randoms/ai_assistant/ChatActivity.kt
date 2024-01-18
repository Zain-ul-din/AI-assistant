package com.randoms.ai_assistant

import com.randoms.ai_assistant.base.BindAbleActivity
import com.randoms.ai_assistant.databinding.ActivityChatBinding

class ChatActivity: BindAbleActivity<ActivityChatBinding>() {
    override fun init(): ActivityChatBinding {
        val binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        return binding;
    }
}
