package com.kotlininaction.ai_assistant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Responsible for displaying splash screen to n duration and transitioning to target activity
 */
class MainActivity : AppCompatActivity() {

    object Options {
        /**
         * Specifies the duration for displaying the splash screen
         */
        const val SPLASH_SCREEN_DURATION: Long = 2000L;

        /**
         * Specifies the target activity to load after splash screen time out
         */
        val TARGET_ACTIVITY = CredentialActivity::class.java;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            delay(MainActivity.Options.SPLASH_SCREEN_DURATION)
            loadApp()
        }
    }

    /**
     initiate app by loading target activity
     **/
    private fun loadApp () {
        startActivity(Intent(this, MainActivity.Options.TARGET_ACTIVITY));
    }
}

