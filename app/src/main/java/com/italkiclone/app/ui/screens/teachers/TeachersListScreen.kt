package com.italkiclone.app.ui.screens.teachers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.italkiclone.app.ui.components.LoadingSkeletonList
import com.italkiclone.app.ui.state.UiState

@Composable
fun TeachersListScreen(
    modifier: Modifier = Modifier,
    viewModel: TeachersViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    when (val current = state) {
        is UiState.Loading -> LoadingSkeletonList(modifier = modifier.fillMaxSize())
        is UiState.Success -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                itemsIndexed(current.data, key = { _, teacher -> teacher.id }) { _, teacher ->
                    TeacherCard(
                        teacher = teacher,
                        modifier = Modifier.animateItem(),
                    )
                }
            }
        }
    }
}
