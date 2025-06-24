package com.example.app_notification_types

import android.app.RemoteInput
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ReplyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val KEY_TEXT_REPLY = "key_text_reply"
        val replyText = RemoteInput.getResultsFromIntent(intent)?.getCharSequence(KEY_TEXT_REPLY)
        if (replyText != null) {
            // Handle the reply text here
            Log.d("ReplyReceiver", "User replied: $replyText")
            // You can update the notification, save to DB, etc.
        }
    }
}