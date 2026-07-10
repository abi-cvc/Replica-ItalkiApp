package com.italkiclone.app.data.model

enum class StudentRelation { CURRENT, POTENTIAL }

data class Student(
    val id: String,
    val name: String,
    val countryFlagEmoji: String,
    val languagesSpoken: List<String>,
    val lastClassLabel: String,
    val totalLessons: Int,
    val relation: StudentRelation,
)
