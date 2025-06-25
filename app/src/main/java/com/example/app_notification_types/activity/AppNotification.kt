package com.example.app_notification_types.activity

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.example.app_notification_types.R
import com.example.app_notification_types.ReplyBroadcastReceiver
import com.example.app_notification_types.databinding.ActivityAppNotificationBinding

class AppNotification : AppCompatActivity() {
    private lateinit var binding: ActivityAppNotificationBinding

    companion object {
        private const val CHANNEL_ID = "test_channel_id"
        private const val NOTIFICATION_ID = 1
    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAppNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create the notification channel
        createNotificationChannel()

        // Set click listener on the button to show the notification
        //Simple Notification
        binding.btn1.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
            }

            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle("Test Notification")
                .setContentText("This is a test notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(this)) {
                notify(NOTIFICATION_ID, builder.build())
            }
        }

        //Inbox Style Notification
        binding.btn2.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
            }

            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle("5 new emails")
                .setStyle(NotificationCompat.InboxStyle()
                    .addLine("Email 1")
                    .addLine("Email 2")
                    .addLine("Email 3"))

            with(NotificationManagerCompat.from(this)) {
                notify(NOTIFICATION_ID, builder.build())
            }
        }

        //Big Picture Notification
        binding.btn3.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
            }

            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle("Image Notification")
                .setStyle(NotificationCompat.BigPictureStyle()
                    .bigPicture(BitmapFactory.decodeResource(resources, R.drawable.app_icon)))

            with(NotificationManagerCompat.from(this)) {
                notify(NOTIFICATION_ID, builder.build())
            }
        }

        //Progress Notification
        binding.btn4.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
            }

            val progress = 50

            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Download in progress")
                .setSmallIcon(R.drawable.app_icon)
                .setProgress(100, progress, false)

            with(NotificationManagerCompat.from(this)) {
                notify(NOTIFICATION_ID, builder.build())
            }
        }

        //Heads-up Notification
        binding.btn5.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
            }

            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle("Urgent Alert")
                .setContentText("Respond immediately")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL) // Sound, vibration, etc.


            with(NotificationManagerCompat.from(this)) {
                notify(NOTIFICATION_ID, builder.build())
            }
        }

        binding.btn5.setOnLongClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
            }

            // Unique key for the input
            val KEY_TEXT_REPLY = "key_text_reply"

            // Create the RemoteInput
            val remoteInput = RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel("Type your response")
                .build()

            // Intent to receive the reply
            val replyIntent = Intent(this, ReplyBroadcastReceiver::class.java) // You'll create this class
            val replyPendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                replyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )

            // Add the action with the remote input
            val action = NotificationCompat.Action.Builder(
                R.drawable.app_icon, // your reply icon
                "Reply",
                replyPendingIntent
            ).addRemoteInput(remoteInput)
                .build()

            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle("Hello")
                .setContentText("Test@1234")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .addAction(action) // Add the reply action here

            with(NotificationManagerCompat.from(this)) {
                notify(NOTIFICATION_ID, builder.build())
            }
            true
        }

//        binding.btn6.setOnClickListener {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
//            }
//
//            // Define intents for each action
//            val prevIntent = Intent(this, MediaReceiver::class.java).setAction("ACTION_PREVIOUS")
//            val playPauseIntent = Intent(this, MediaReceiver::class.java).setAction("ACTION_PLAY_PAUSE")
//            val nextIntent = Intent(this, MediaReceiver::class.java).setAction("ACTION_NEXT")
//
//            val prevPendingIntent = PendingIntent.getBroadcast(
//                this, 0, prevIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
//            )
//            val playPausePendingIntent = PendingIntent.getBroadcast(
//                this, 1, playPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
//            )
//            val nextPendingIntent = PendingIntent.getBroadcast(
//                this, 2, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
//            )
//
//            val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.app_icon)
//            // Build the notification
//            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.drawable.app_icon)
//                .setContentTitle("Ae Watan")
//                .setContentText("Song by Arijit Singh")
//                .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
//                    .setShowActionsInCompactView(0, 1, 2) // Indexes of the actions
//                )
//                .setPriority(NotificationCompat.PRIORITY_LOW)
//                .addAction(R.drawable.previous, "Previous", prevPendingIntent)
//                .addAction(R.drawable.play, "Play/Pause", playPausePendingIntent)
//                .addAction(R.drawable.pause, "Play/Pause", playPausePendingIntent)
//                .addAction(R.drawable.next, "Next", nextPendingIntent)
//                .setLargeIcon(largeIcon)
////                .setStyle(androidx.media.app.NotificationCompat.MediaStyle())
//                .setOnlyAlertOnce(true)
//                .setAutoCancel(false)
//
//            with(NotificationManagerCompat.from(this)) {
//                notify(NOTIFICATION_ID, builder.build())
//            }
//        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

