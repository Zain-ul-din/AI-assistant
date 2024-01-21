package com.randoms.ai_assistant

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.randoms.ai_assistant.base.BindAbleActivity
import com.randoms.ai_assistant.databinding.ActivityMainBinding
import com.randoms.ai_assistant.extensions.executeAfterDelay

/**
 * Responsible for displaying splash screen to n duration and transitioning to target activity
 */
class MainActivity : BindAbleActivity<ActivityMainBinding>() {

    object Options {
        /**
         * Specifies the duration for displaying the splash screen
         */
        const val SPLASH_SCREEN_DURATION: Long = 2000L;

        /**
         * Specifies the target activity to load after splash screen time out
         */
        val TARGET_ACTIVITY = HomeActivity::class.java;
    }

    override fun init(): ActivityMainBinding {
        val binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root);
        Options.SPLASH_SCREEN_DURATION.executeAfterDelay(lifecycleScope) {
            runApp()
        };
        return binding;
    }

    /**
     initiate app by loading target activity
     **/
    private fun runApp () {
        startActivity(Intent(this, MainActivity.Options.TARGET_ACTIVITY));
    }
}
