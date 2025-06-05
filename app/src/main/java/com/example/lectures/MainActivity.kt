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
import com.example.lectures.data.LecturesData
import com.example.lectures.data.SubjectsData
import com.example.lectures.data.TimeSlotsData

import com.example.lectures.data.TimetableData
import com.example.lectures.data.TimetableManager
import com.example.lectures.ui.theme.LecturesTheme


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
                                    when (timetableManager.detectJsonType(json)) {
                                        TimetableManager.JsonType.TIME_SLOTS -> {
                                            val timeSlotsData = TimeSlotsData.fromJson(json)
                                            timetableManager.saveTimeSlots(timeSlotsData)
                                            Toast.makeText(this, "Time slots updated!", Toast.LENGTH_SHORT).show()
                                        }
                                        TimetableManager.JsonType.SUBJECTS -> {
                                            val subjectsData = SubjectsData.fromJson(json)
                                            timetableManager.saveSubjects(subjectsData)
                                            Toast.makeText(this, "Subjects updated!", Toast.LENGTH_SHORT).show()
                                        }
                                        TimetableManager.JsonType.LECTURES -> {
                                            val lecturesData = LecturesData.fromJson(json)
                                            timetableManager.saveLectures(lecturesData)
                                            Toast.makeText(this, "Lectures updated!", Toast.LENGTH_SHORT).show()
                                        }
                                        TimetableManager.JsonType.FULL_TIMETABLE -> {
                                            val timetableData = TimetableData.fromJson(json)
                                            timetableManager.saveTimetableFromData(timetableData)
                                            Toast.makeText(this, "Full timetable updated!", Toast.LENGTH_SHORT).show()
                                        }
                                        TimetableManager.JsonType.UNKNOWN -> {
                                            Toast.makeText(this, "Invalid JSON format!", Toast.LENGTH_LONG).show()
                                            return@PasteJsonDialog
                                        }
                                    }
                                    // Refresh the current timetable
                                    currentTimetableState.value = timetableManager.getCurrentTimetable()
                                    showPasteDialog.value = false
                                } catch (e: Exception) {
                                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
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

