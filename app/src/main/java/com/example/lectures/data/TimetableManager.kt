package com.example.lectures.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File


class TimetableManager(private val context: Context) {
    private val gson = Gson()

    private val timeSlotsFile: File
        get() = File(context.filesDir, "time_slots.json")
    private val subjectsFile: File
        get() = File(context.filesDir, "subjects.json")
    private val lecturesFile: File
        get() = File(context.filesDir, "lectures.json")

    fun saveTimeSlots(timeSlotsData: TimeSlotsData) {
        timeSlotsFile.writeText(gson.toJson(timeSlotsData))
    }

    fun saveSubjects(subjectsData: SubjectsData) {
        subjectsFile.writeText(gson.toJson(subjectsData))
    }

    fun saveLectures(lecturesData: LecturesData) {
        lecturesFile.writeText(gson.toJson(lecturesData))
    }

    fun getTimeSlots(): TimeSlotsData? {
        return try {
            if (timeSlotsFile.exists()) {
                val json = timeSlotsFile.readText()
                TimeSlotsData.fromJson(json)
            } else null
        } catch (e: Exception) {
            null
        }
    }

    fun getSubjects(): SubjectsData? {
        return try {
            if (subjectsFile.exists()) {
                val json = subjectsFile.readText()
                SubjectsData.fromJson(json)
            } else null
        } catch (e: Exception) {
            null
        }
    }

    fun getLectures(): LecturesData? {
        return try {
            if (lecturesFile.exists()) {
                val json = lecturesFile.readText()
                LecturesData.fromJson(json)
            } else null
        } catch (e: Exception) {
            null
        }
    }

    fun getCurrentTimetable(): TimetableData? {
        val timeSlots = getTimeSlots()?.timeSlots ?: return null
        val lectures = getLectures()?.lectures ?: return null

        // If we have both timeSlots and lectures, we can show the timetable
        // Use default white color for all subjects if subjects data is not available
        val subjects = getSubjects()?.subjects ?: lectures.values
            .flatten()
            .map { it[1] } // Get all unique subject names from lectures
            .distinct()
            .associateWith { "0xFFFFFFFF" } // Map each subject to white color

        return TimetableData(timeSlots, subjects, lectures)
    }

    fun saveTimetableFromData(timetableData: TimetableData) {
        saveTimeSlots(TimeSlotsData(timetableData.timeSlots))
        saveSubjects(SubjectsData(timetableData.subjects))
        saveLectures(LecturesData(timetableData.lectures))
    }

    fun detectJsonType(json: String): JsonType {
        return try {
            val type = object : TypeToken<Map<String, Any>>() {}.type
            val map: Map<String, Any> = gson.fromJson(json, type)

            when {
                map.containsKey("timeSlots") && !map.containsKey("subjects") && !map.containsKey("lectures") -> JsonType.TIME_SLOTS
                !map.containsKey("timeSlots") && map.containsKey("subjects") && !map.containsKey("lectures") -> JsonType.SUBJECTS
                !map.containsKey("timeSlots") && !map.containsKey("subjects") && map.containsKey("lectures") -> JsonType.LECTURES
                map.containsKey("timeSlots") && map.containsKey("subjects") && map.containsKey("lectures") -> JsonType.FULL_TIMETABLE
                else -> JsonType.UNKNOWN
            }
        } catch (e: Exception) {
            JsonType.UNKNOWN
        }
    }

    enum class JsonType {
        TIME_SLOTS,
        SUBJECTS,
        LECTURES,
        FULL_TIMETABLE,
        UNKNOWN
    }
} 