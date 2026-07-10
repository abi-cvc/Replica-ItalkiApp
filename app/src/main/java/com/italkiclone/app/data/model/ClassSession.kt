package com.italkiclone.app.data.model

enum class ClassStatus(val label: String) {
    ACTION_REQUIRED("Acción requerida"),
    UPCOMING("Próxima"),
    WAITING("En espera"),
    COMPLETED("Completada"),
    CANCELLED("Cancelada"),
}

data class ClassSession(
    val id: String,
    val participantName: String,
    val classTitle: String,
    val subject: String,
    val dateTimeLabel: String,
    val durationMinutes: Int,
    val status: ClassStatus,
)
