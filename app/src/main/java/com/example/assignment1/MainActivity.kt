package com.example.assignment1

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import com.example.assignment1.ui.theme.ProjectTheme
import com.example.assignment1.services.TimerService
import com.example.assignment1.ui.preset.TimerScreen


@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    private var isBound = mutableStateOf(false)
    private lateinit var stopwatchService: TimerService

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as TimerService.TimerBinder
            stopwatchService = binder.getService()
            isBound.value = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            isBound.value = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Intent(this, TimerService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
        setContent {
            ProjectTheme {
                Surface {
                    if(isBound.value) {
                        TimerScreen(timerService = stopwatchService)
                    }
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
        isBound.value = false
    }
}




