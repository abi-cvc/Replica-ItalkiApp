package com.italkiclone.app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.italkiclone.app.data.model.ClassSession
import com.italkiclone.app.data.model.ClassStatus
import com.italkiclone.app.data.repository.AppContainer
import com.italkiclone.app.domain.repository.ClassRepository
import com.italkiclone.app.ui.state.UiState
import kotlin.random.Random
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TeacherHomeStats(
    val balanceUsd: Double,
    val upcomingCount: Int,
    val actionRequiredCount: Int,
    val packageActionCount: Int,
    val completedClassesCount: Int,
    val responseRatePercent: Int,
    val attendanceRatePercent: Int,
)

/** Resuelve la próxima clase del estudiante (para que "Hoy" abra con algo accionable
 * en vez de solo contenido promocional) y las métricas del panel de profesor,
 * derivadas de las mismas [ClassSession] que alimentan la lista de Clases. */
class HoyViewModel(
    private val repository: ClassRepository = AppContainer.classRepository,
) : ViewModel() {

    private val _nextClass = MutableStateFlow<UiState<ClassSession?>>(UiState.Loading)
    val nextClass: StateFlow<UiState<ClassSession?>> = _nextClass.asStateFlow()

    private val _teacherStats = MutableStateFlow<UiState<TeacherHomeStats>>(UiState.Loading)
    val teacherStats: StateFlow<UiState<TeacherHomeStats>> = _teacherStats.asStateFlow()

    init {
        viewModelScope.launch {
            val classes = repository.getClasses()
            _nextClass.value = UiState.Success(classes.firstOrNull { it.status == ClassStatus.UPCOMING })

            val random = Random(42)
            _teacherStats.value = UiState.Success(
                TeacherHomeStats(
                    balanceUsd = 800.0 + random.nextInt(0, 400_00) / 100.0,
                    upcomingCount = classes.count { it.status == ClassStatus.UPCOMING },
                    actionRequiredCount = classes.count { it.status == ClassStatus.ACTION_REQUIRED },
                    packageActionCount = classes.count { it.status == ClassStatus.WAITING },
                    completedClassesCount = classes.count { it.status == ClassStatus.COMPLETED },
                    responseRatePercent = 90 + random.nextInt(0, 10),
                    attendanceRatePercent = 88 + random.nextInt(0, 12),
                ),
            )
        }
    }
}
