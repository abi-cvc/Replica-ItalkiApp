package com.italkiclone.app.ui.screens.classes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.italkiclone.app.data.model.ClassStatus
import com.italkiclone.app.ui.components.LoadingSkeletonList
import com.italkiclone.app.ui.state.UiState

private val tabs = listOf(null) + ClassStatus.entries

@Composable
fun ClassesListScreen(
    modifier: Modifier = Modifier,
    viewModel: ClassesViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }

    when (val current = state) {
        is UiState.Loading -> LoadingSkeletonList(modifier = modifier.fillMaxSize())
        is UiState.Success -> {
            val all = current.data
            Column(modifier = modifier.fillMaxSize()) {
                ScrollableTabRow(selectedTabIndex = selectedTab, edgePadding = 12.dp) {
                    tabs.forEachIndexed { index, status ->
                        val count = if (status == null) all.size else all.count { it.status == status }
                        val label = if (status == null) "Todos" else status.label
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text("$label · $count") },
                        )
                    }
                }

                val filtered = tabs[selectedTab].let { status ->
                    if (status == null) all else all.filter { it.status == status }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(filtered, key = { it.id }) { session ->
                        ClassCard(session = session, modifier = Modifier.animateItem())
                    }
                }
            }
        }
    }
}
