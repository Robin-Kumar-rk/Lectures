package com.example.lectures.data

data class TimetableData(
    val timeSlots: Map<String, String>,
    val subjects: Map<String, String>,
    val lectures: Map<String, List<List<String>>>
) 