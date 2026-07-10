package com.italkiclone.app.ui.screens.students

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.italkiclone.app.data.model.Student
import com.italkiclone.app.data.repository.AppContainer
import com.italkiclone.app.domain.repository.StudentRepository
import com.italkiclone.app.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentsViewModel(
    private val repository: StudentRepository = AppContainer.studentRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Student>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Student>>> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getStudents())
        }
    }
}
