package com.italkiclone.app.ui.screens.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.italkiclone.app.data.model.ClassSession
import com.italkiclone.app.data.repository.AppContainer
import com.italkiclone.app.domain.repository.ClassRepository
import com.italkiclone.app.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClassesViewModel(
    private val repository: ClassRepository = AppContainer.classRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ClassSession>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<ClassSession>>> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getClasses())
        }
    }
}
