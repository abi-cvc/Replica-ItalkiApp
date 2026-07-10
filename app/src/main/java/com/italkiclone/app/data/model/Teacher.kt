package com.italkiclone.app.data.model

data class Teacher(
    val id: String,
    val name: String,
    val countryFlagEmoji: String,
    val isProfessional: Boolean,
    val teachingLanguage: String,
    val nativeLanguage: String,
    val bio: String,
    val pricePerHour: Double,
    val discountPercent: Int?,
    val rating: Double,
    val totalClasses: Int,
    val isNew: Boolean,
    val photoSeed: String,
    val interestTags: List<String>,
)
