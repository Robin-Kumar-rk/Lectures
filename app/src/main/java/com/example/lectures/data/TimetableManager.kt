package com.example.lectures.data

import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader

class TimetableManager(private val context: Context) {
    private val gson = Gson()
    private val timetableFile: File
        get() = File(context.filesDir, "current_timetable.json")


    fun saveTimetableFromData(timetableData: TimetableData) {
        FileOutputStream(timetableFile).use { outputStream ->
            outputStream.write(gson.toJson(timetableData).toByteArray())
        }
    }

    fun getCurrentTimetable(): TimetableData? {
        return try {
            if (timetableFile.exists()) {
                val json = timetableFile.readText()
                gson.fromJson<TimetableData>(json, object : TypeToken<TimetableData>() {}.type)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
} 