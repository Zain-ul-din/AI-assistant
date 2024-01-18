package com.randoms.ai_assistant

import com.randoms.ai_assistant.base.BindAbleActivity
import com.randoms.ai_assistant.databinding.ActivityHomeBinding

class HomeActivity : BindAbleActivity<ActivityHomeBinding>() {
    override fun init(): ActivityHomeBinding {
        val binding = ActivityHomeBinding.inflate(layoutInflater);
        setContentView(binding.root)
        return binding
    }

}
