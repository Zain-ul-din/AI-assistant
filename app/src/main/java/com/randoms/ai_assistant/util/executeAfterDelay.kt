package com.randoms.ai_assistant.util

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Executes action after given delay
 */
fun executeAfterDelay(scope: LifecycleCoroutineScope,ms: Long, action: ()-> Unit) {
    scope.launch {
        delay(ms)
        action()
    }
}
