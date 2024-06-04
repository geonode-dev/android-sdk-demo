package com.geonode.testsdk

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.app.NotificationCompat
import androidx.lifecycle.lifecycleScope
import com.repocket.androidsdk.Repocket
import com.repocket.androidsdk.models.ConnectionEvent
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val TAG = "SDK-TEST"
    private val channelId = "RepocketSDKChannel"
    private val channelName = "RepocketSDKChannel"
    private val channelDescription = "Repocket SDK needs this channel to run in background"
    private val iconResId = R.drawable.ic_launcher_foreground

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val textView = findViewById<TextView>(R.id.connection_status)


        createNotificationChannel()
        val notification = createNotification()

        val repocket =
            Repocket.Builder().withApiKey("YOUR-API-KEY").withContext(this)
                .withForegroundService(true, notificationId = 11, notification).build()

        lifecycleScope.launch {
            repocket.connectionStatus.collect {
                when (it) {
                    ConnectionEvent.Connected -> {
                        textView.text = "connected"
                        Log.d(TAG, "connected")

                    }

                    ConnectionEvent.Connecting -> {
                        Log.d(TAG, "Connecting")
                        textView.text = "connecting"
                    }

                    ConnectionEvent.Disconnected -> {
                        Log.d(TAG, "DisConnected")
                        textView.text = "disconnect"

                    }

                    is ConnectionEvent.Error -> {
                        Log.d(TAG, it.ex.message.toString())

                        textView.text = "Error"
                    }

                    ConnectionEvent.OnRefreshTokenRequired -> {
                        textView.text = "on Refresh Token"
                    }
                }

            }
        }

        findViewById<Button>(R.id.start).setOnClickListener {
            repocket.connect()
        }
        findViewById<Button>(R.id.disconnect).setOnClickListener {
            repocket.disconnect()
        }

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, channelId).setSmallIcon(iconResId)
            .setContentTitle("Repocket SDK").setContentText("Repocket SDK is running in background")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()
    }
}