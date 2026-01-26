package com.oliinyk.yaroslav.easyreads.domain.service

import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants.FIREBASE_CLOUD_MESSAGING_NOTIFICATION_CHANNEL_ID
import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants.FIREBASE_CLOUD_MESSAGING_SERVICE_ID

class FirebaseNotificationService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        message.notification?.let {
            sendNotification(it)
        }
    }

    private fun sendNotification(notification: RemoteMessage.Notification) {
        val notification =
            NotificationCompat
                .Builder(applicationContext, FIREBASE_CLOUD_MESSAGING_NOTIFICATION_CHANNEL_ID)
                .setContentTitle(notification.title)
                .setTicker(notification.ticker)
                .setContentText(notification.body)
                .setContentInfo(notification.body)
                .setStyle(NotificationCompat.BigTextStyle().bigText(notification.body))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .build()

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(FIREBASE_CLOUD_MESSAGING_SERVICE_ID, notification)
    }
}
