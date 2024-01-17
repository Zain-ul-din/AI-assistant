package com.randoms.ai_assistant

import com.randoms.ai_assistant.base.BindAbleActivity
import com.randoms.ai_assistant.databinding.ActivityCredentialBinding


class CredentialActivity : BindAbleActivity<ActivityCredentialBinding>() {
    override fun init(): ActivityCredentialBinding {
        val binding = ActivityCredentialBinding.inflate(layoutInflater);
        setContentView(binding.root)
        return binding;
    }
}
