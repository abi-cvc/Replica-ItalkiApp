package com.italkiclone.app.data.model

enum class StudentRelation { CURRENT, POTENTIAL }

data class LanguageCompetency(
    val language: String,
    val isNative: Boolean,
    val proficiencyLevel: Int = 5,
)

data class Student(
    val id: String,
    val name: String,
    val countryFlagEmoji: String,
    val relation: StudentRelation,
    // Campos de la tab "Actual"
    val languagesSpoken: List<String> = emptyList(),
    val lastClassLabel: String = "",
    val totalLessons: Int = 0,
    // Campos de la tab "Potencial"
    val possibleLearningLanguage: String = "",
    val possibleLanguageLevel: Int = 0,
    val profileTags: List<String> = emptyList(),
    val languageCompetencies: List<LanguageCompetency> = emptyList(),
)
