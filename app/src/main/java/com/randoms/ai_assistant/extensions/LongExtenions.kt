package com.randoms.ai_assistant.extensions

import androidx.lifecycle.LifecycleCoroutineScope
import com.randoms.ai_assistant.util.executeAfterDelay

fun Long.executeAfterDelay(scope: LifecycleCoroutineScope, action: ()-> Unit) {
    executeAfterDelay(scope, this, action)
}
