package com.example.lectures

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.lectures.data.TimetableData
import com.example.lectures.data.TimetableManager
import com.example.lectures.ui.theme.LecturesTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    private lateinit var dateChangeReceiver: BroadcastReceiver
    private lateinit var timetableManager: TimetableManager
    private val currentDateState = mutableStateOf(getCurrentDay())
    private val currentTimetableState = mutableStateOf<TimetableData?>(null)
    private val showPasteDialog = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        timetableManager = TimetableManager(this)
        currentTimetableState.value = timetableManager.getCurrentTimetable()

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
                    HomeView(
                        currentDateState = currentDateState,
                        currentTimetable = currentTimetableState.value,
                        onPasteTimetable = { showPasteDialog.value = true }
                    )
                    if (showPasteDialog.value) {
                        PasteJsonDialog(
                            onPaste = { json ->
                                try {
                                    val timetableData = Gson().fromJson(json, TimetableData::class.java)
                                    timetableManager.saveTimetableFromData(timetableData)
                                    currentTimetableState.value = timetableData
                                    Toast.makeText(this, "Timetable updated!", Toast.LENGTH_SHORT).show()
                                    showPasteDialog.value = false
                                } catch (e: Exception) {
                                    Toast.makeText(this, "Invalid JSON!", Toast.LENGTH_LONG).show()
                                }
                            },
                            onDismiss = { showPasteDialog.value = false }
                        )
                    }
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
        finishAffinity()
    }
}

