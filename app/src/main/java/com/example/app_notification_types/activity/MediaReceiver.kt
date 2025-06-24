package com.example.app_notification_types.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MediaReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            "ACTION_PREVIOUS" -> {
                Toast.makeText(context, "Previous clicked", Toast.LENGTH_SHORT).show()
                Log.d("MediaReceiver", "Previous clicked")
            }
            "ACTION_PLAY_PAUSE" -> {
                Toast.makeText(context, "Play/Pause clicked", Toast.LENGTH_SHORT).show()
                Log.d("MediaReceiver", "Play/Pause clicked")
            }
            "ACTION_NEXT" -> {
                Toast.makeText(context, "Next clicked", Toast.LENGTH_SHORT).show()
                Log.d("MediaReceiver", "Next clicked")
            }
        }
    }
}