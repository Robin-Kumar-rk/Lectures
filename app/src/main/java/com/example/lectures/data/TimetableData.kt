package com.example.lectures.data

data class TimeSlotsData(
    val timeSlots: Map<String, String>
) {
    companion object {
        fun fromJson(json: String): TimeSlotsData {
            val gson = com.google.gson.Gson()
            val type = object : com.google.gson.reflect.TypeToken<Map<String, Any>>() {}.type
            val map: Map<String, Any> = gson.fromJson(json, type)
            return TimeSlotsData(
                timeSlots = map["timeSlots"] as Map<String, String>
            )
        }
    }
}

data class SubjectsData(
    val subjects: Map<String, String>
) {
    companion object {
        fun fromJson(json: String): SubjectsData {
            val gson = com.google.gson.Gson()
            val type = object : com.google.gson.reflect.TypeToken<Map<String, Any>>() {}.type
            val map: Map<String, Any> = gson.fromJson(json, type)
            val subjectsMap = (map["subjects"] as Map<String, String>).mapKeys { it.key.uppercase() }
            return SubjectsData(subjects = subjectsMap)
        }
    }
}

data class LecturesData(
    val lectures: Map<String, List<List<String>>>
) {
    companion object {
        fun fromJson(json: String): LecturesData {
            val gson = com.google.gson.Gson()
            val type = object : com.google.gson.reflect.TypeToken<Map<String, Any>>() {}.type
            val map: Map<String, Any> = gson.fromJson(json, type)
            val lecturesMap = (map["lectures"] as Map<String, List<List<String>>>).mapValues { (_, lectures) ->
                lectures.map { lecture ->
                    listOf(
                        lecture[0], // period
                        lecture[1].uppercase(), // subject
                        lecture[2] // time slot
                    )
                }
            }
            return LecturesData(lectures = lecturesMap)
        }
    }
}

data class TimetableData(
    val timeSlots: Map<String, String>,
    val subjects: Map<String, String>,
    val lectures: Map<String, List<List<String>>>
) {
    init {
        subjects.keys.forEach { key ->
            if (key != key.uppercase()) {
                throw IllegalArgumentException("Subject names must be in uppercase: $key")
            }
        }
    }

    // Create a case-insensitive map for subject lookups
    private val caseInsensitiveSubjects = subjects.mapKeys { it.key.uppercase() }

    fun getSubjectColor(subject: String): String? {
        return caseInsensitiveSubjects[subject.uppercase()]
    }

    companion object {
        fun fromJson(json: String): TimetableData {
            val gson = com.google.gson.Gson()
            val type = object : com.google.gson.reflect.TypeToken<Map<String, Any>>() {}.type
            val map: Map<String, Any> = gson.fromJson(json, type)
            
            return TimetableData(
                timeSlots = map["timeSlots"] as Map<String, String>,
                subjects = (map["subjects"] as Map<String, String>).mapKeys { it.key.uppercase() },
                lectures = (map["lectures"] as Map<String, List<List<String>>>).mapValues { (_, lectures) ->
                    lectures.map { lecture ->
                        listOf(
                            lecture[0],
                            lecture[1].uppercase(),
                            lecture[2]
                        )
                    }
                }
            )
        }
    }
} 