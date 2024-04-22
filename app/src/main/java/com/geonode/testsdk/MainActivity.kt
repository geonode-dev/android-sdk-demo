package com.geonode.testsdk

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.geonode.testsdk.ui.theme.TestSdkTheme
import com.repocket.androidsdk.Repocket
import com.repocket.androidsdk.models.ConnectionEvent
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val TAG = "SDK-TEST"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val textView = findViewById<TextView>(R.id.connection_status)


        val repocket =
            Repocket.Builder().withContext(this).withApiKey("YOUR-API-KEY").withForegroundService(true,"Notification Title","Notification Content")
                .build()
        lifecycleScope.launch {
            repocket.connectionStatus.collect {
                when (it) {
                    ConnectionEvent.Connected -> {
                        textView.text = "connected"

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
}