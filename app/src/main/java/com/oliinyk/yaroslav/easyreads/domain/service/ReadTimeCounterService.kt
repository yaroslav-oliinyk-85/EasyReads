package com.oliinyk.yaroslav.easyreads.domain.service

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
import android.os.Build
import android.os.Handler
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.repository.ReadTimeCounterRepository
import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants.READ_TIME_COUNTER_NOTIFICATION_CHANNEL_ID
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID
import javax.inject.Inject

private const val READ_TIME_COUNTER_SERVICE_ID = 1
private const val NOTIFICATION_UPDATE_DELAY = 1000L

@AndroidEntryPoint
class ReadTimeCounterService : Service() {
    @Inject
    lateinit var readTimeCounterRepository: ReadTimeCounterRepository

    private lateinit var handler: Handler

    private var isServiceRunning = false
    private var isServiceResumed = false
    private var bookTitle: String = ""

    override fun onCreate() {
        super.onCreate()
        handler = Handler(mainLooper)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        intent?.let {
            it.action?.let { action ->
                when (Actions.valueOf(action)) {
                    Actions.START -> {
                        if (!isServiceRunning) {
                            isServiceRunning = true
                            isServiceResumed = true

                            bookTitle = it.getStringExtra("bookTitle") ?: ""

                            val bookId: String? = it.getStringExtra("bookId")
                            val pageCurrent: Int = it.getIntExtra("pageCurrent", 0)
                            bookId?.let { id ->
                                readTimeCounterRepository.start(UUID.fromString(id), pageCurrent)
                            }

                            handler.post(
                                object : Runnable {
                                    override fun run() {
                                        if (isServiceRunning) {
                                            if (isServiceResumed) {
                                                updateNotification(getContentText())
                                            }
                                            handler.postDelayed(this, NOTIFICATION_UPDATE_DELAY)
                                        }
                                    }
                                },
                            )

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                startForeground(
                                    READ_TIME_COUNTER_SERVICE_ID,
                                    createNotification(),
                                    FOREGROUND_SERVICE_TYPE_DATA_SYNC,
                                )
                            } else {
                                startForeground(
                                    READ_TIME_COUNTER_SERVICE_ID,
                                    createNotification(),
                                )
                            }
                        }
                    }
                    Actions.RESUME -> {
                        isServiceResumed = true
                        readTimeCounterRepository.resume()
                    }
                    Actions.PAUSE -> {
                        isServiceResumed = false
                        readTimeCounterRepository.pause()
                    }
                    Actions.STOP -> {
                        isServiceRunning = false
                        readTimeCounterRepository.stop()
                        stopSelf()
                    }
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotification(contentText: String = "00:00"): Notification =
        NotificationCompat
            .Builder(this, READ_TIME_COUNTER_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle(getString(R.string.read_time_counter_notification_content_title_text))
            .setContentTitle(bookTitle)
            .setContentText(contentText)
            .setPriority(Notification.PRIORITY_LOW)
            .setOngoing(true)
            .build()

    private fun updateNotification(content: String) {
        val notification = createNotification(content)
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(READ_TIME_COUNTER_SERVICE_ID, notification)
    }

    private fun getContentText(): String {
        val readingSession = readTimeCounterRepository.getReadingSession()
        return getString(
            R.string.read_time_counter_notification_content_text,
            readingSession.readHours,
            readingSession.readMinutes,
            readingSession.readSeconds,
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        readTimeCounterRepository.stop()
        isServiceRunning = false
        bookTitle = ""
    }

    enum class Actions {
        START,
        RESUME,
        PAUSE,
        STOP,
    }
}
