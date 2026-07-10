package com.italkiclone.app.ui.screens.teachers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.italkiclone.app.data.model.Teacher
import com.italkiclone.app.data.repository.AppContainer
import com.italkiclone.app.domain.repository.TeacherRepository
import com.italkiclone.app.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TeachersViewModel(
    private val repository: TeacherRepository = AppContainer.teacherRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Teacher>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Teacher>>> = _uiState.asStateFlow()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getTeachers())
        }
    }
}
