package com.randoms.ai_assistant.util

import android.content.Context
import android.content.Intent
import android.net.Uri

object UrlUtils {
    // source:
    //  https://stackoverflow.com/questions/2201917/how-can-i-open-a-url-in-androids-web-browser-from-my-application
    fun openUrl(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
}
