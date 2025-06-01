package com.example.lectures.data


import androidx.compose.ui.graphics.Color
import com.example.lectures.models.Lecture


object Graph {
    val a = "8:50-9:40"
    val b = "9:40-10:30"
    val c = "10:30-11:20"
    val d = "11:20-12:10"
    val e = "12:10-1:00"
    val f = "1:00-1:50"
    val g = "1:50-2:40"
    val h = "2:40-3:30"
    val i = "3:30-4:20"
    val ab = "8:50-10:30"
    val cd = "10:30-12:10"
    val bc = "9:40-11:20"
    val de = "11:20-12:10"
    val gh = "1:50-3:30"
    val hi = "2:40-4:20"
    val tafl = "TAFL"
    val tc = "TC"
    val os = "OS"
    val java = "Java"
    val dl = "DE"
    val cs = "CS"
    val lab1 = "B1 OS LAB 7\nB2 CS LAB 8"
    val lab2 = "B1 Java LAB 7\nB2 OS LAB 8"
    val lab3 = "B1 CS LAB 8\nB2 Java LAB 7"
    val lunch = "Lunch"
    val library = "Library"
    val sports = "Sports"

    private val subjectColorMap = mapOf(
        tafl to "0xFF76FCFC",
        tc to "0xFF7A61FF",
        os to "0xFFF56DD7",
        java to "0xFFFCE699",
        dl to "0xFFF86F6F",
        cs to "0xFF7EFF74",
        lab1 to "0xFF969696",
        lab2 to "0xFF969696",
        lab3 to "0xFF969696"
    )

    fun getColorForSubject(subject: String): Color {
        val hexColor = subjectColorMap[subject] ?: "0xFFFFFFFF"
        val colorValue = hexColor.substring(2).toLong(16)
        return Color(colorValue)
    }

    val lectures =  mapOf(
        "MONDAY" to listOf(
            Lecture(period = "1", lectureName = tafl, time = a),
            Lecture(period = "2", lectureName = tc, time = b),
            Lecture(period = "3", lectureName = java, time = c),
            Lecture(period = "4", lectureName = dl, time = d),
//                Lecture(period = "", lectureName = lunch, time = f),
//                Lecture(period = "6", lectureName = os, time = g),
//                Lecture(period = "7", lectureName = java, time = h)
        ),
        "TUESDAY" to listOf(
            Lecture(period = "1", lectureName = tafl, time = a),
            Lecture(period = "2", lectureName = tc, time = b),
            Lecture(period = "3", lectureName = lab1, time = cd),
            Lecture(period = "", lectureName = lunch, time = e),
            Lecture(period = "5", lectureName = os, time = f),

            Lecture(period = "6", lectureName = dl, time = h),
            Lecture(period = "7", lectureName = lab2, time = hi)
        ),
        "WEDNESDAY" to listOf(
            Lecture(period = "1", lectureName = java, time = a),
            Lecture(period = "2", lectureName = os, time = b),
            Lecture(period = "3", lectureName = tafl, time = c),
            Lecture(period = "4", lectureName = java, time = d),
//                Lecture(period = "5", lectureName = tafl, time = e),
//                Lecture(period = "", lectureName = lunch, time = f),
//                Lecture(period = "6", lectureName = dl, time = g),
//                Lecture(period = "7", lectureName = os, time = h)
        ),
        "THURSDAY" to listOf(
            Lecture(period = "1", lectureName = lab3, time = ab),
            Lecture(period = "3", lectureName = os, time = c),
            Lecture(period = "4", lectureName = cs, time = d),
            Lecture(period = "", lectureName = lunch, time = e),
            Lecture(period = "5", lectureName = tafl, time = f),
            Lecture(period = "6", lectureName = tafl, time = g),
            Lecture(period = "7", lectureName = os, time = h),
            Lecture(period = "8", lectureName = dl, time = i)
        ),
        "FRIDAY" to listOf(
            Lecture(period = "1", lectureName = java, time = a),
            Lecture(period = "2", lectureName = dl, time = b),
            Lecture(period = "3", lectureName = cs, time = c),
            Lecture(period = "4", lectureName = os, time = d),
//                Lecture(period = "5", lectureName = java, time = e),
//                Lecture(period = "", lectureName = lunch, time = f),
//                Lecture(period = "6", lectureName = tafl, time = g),
//                Lecture(period = "7", lectureName = tc, time = h)
        )
    )

}
