package com.italkiclone.app.ui.screens.chats

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.italkiclone.app.ui.components.LoadingSkeletonList
import com.italkiclone.app.ui.state.UiState

@Composable
fun ChatsListScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatsViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    when (val current = state) {
        is UiState.Loading -> LoadingSkeletonList(modifier = modifier.fillMaxSize())
        is UiState.Success -> {
            LazyColumn(modifier = modifier.fillMaxSize()) {
                items(current.data, key = { it.id }) { chat ->
                    ChatItem(chat = chat, modifier = Modifier.animateItem())
                    HorizontalDivider()
                }
            }
        }
    }
}
