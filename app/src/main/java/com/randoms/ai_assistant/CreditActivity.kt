package com.randoms.ai_assistant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.randoms.ai_assistant.base.BindAbleActivity
import com.randoms.ai_assistant.databinding.ActivityCreditBinding
import com.randoms.ai_assistant.util.UrlUtils

class CreditActivity : BindAbleActivity<ActivityCreditBinding>() {
    override fun init(): ActivityCreditBinding {
        val binding = ActivityCreditBinding.inflate(layoutInflater);
        setContentView(binding.root)
        return binding;
    }

    override fun start() {
        handleRouting()
    }

    fun handleRouting() {
        binding!!.gotItBtn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java));
        }

        binding!!.githubIcon.setOnClickListener {
            UrlUtils.openUrl(this, "https://github.com/Zain-ul-din/AI-assistant");
        }
    }
}