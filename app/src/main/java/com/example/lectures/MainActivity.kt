package com.example.lectures

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lectures.ui.theme.LecturesTheme

class MainActivity : ComponentActivity() {
    private lateinit var dateChangeReceiver: BroadcastReceiver
    private val currentDateState = mutableStateOf(getCurrentDay())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        dateChangeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                currentDateState.value = getCurrentDay()
            }
        }

        setContent {
            LecturesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeView(currentDateState = currentDateState)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(Intent.ACTION_TIME_CHANGED).apply {
            addAction(Intent.ACTION_DATE_CHANGED)
        }
        registerReceiver(dateChangeReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
        try {
            unregisterReceiver(dateChangeReceiver)
        } catch (e: Exception) {
            // Ignore receiver not registered error
        }
    }
}

