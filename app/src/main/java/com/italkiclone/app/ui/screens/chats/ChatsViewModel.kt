package com.italkiclone.app.ui.screens.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.italkiclone.app.data.model.ChatConversation
import com.italkiclone.app.data.repository.AppContainer
import com.italkiclone.app.domain.repository.ChatRepository
import com.italkiclone.app.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatsViewModel(
    private val repository: ChatRepository = AppContainer.chatRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ChatConversation>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<ChatConversation>>> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getChats())
        }
    }
}
