package com.italkiclone.app.ui.screens.students

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import com.italkiclone.app.data.model.StudentRelation
import com.italkiclone.app.ui.components.LoadingSkeletonList
import com.italkiclone.app.ui.state.UiState

@Composable
fun StudentsListScreen(
    modifier: Modifier = Modifier,
    viewModel: StudentsViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }

    when (val current = state) {
        is UiState.Loading -> LoadingSkeletonList(modifier = modifier.fillMaxSize())
        is UiState.Success -> {
            val relation = if (selectedTab == 0) StudentRelation.CURRENT else StudentRelation.POTENTIAL
            val filtered = current.data.filter { it.relation == relation }

            Column(modifier = modifier.fillMaxSize()) {
                TabRow(selectedTabIndex = selectedTab) {
                    Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("Actual") })
                    Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("Potencial") })
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(filtered, key = { it.id }) { student ->
                        StudentCard(student = student, modifier = Modifier.animateItem())
                    }
                }
            }
        }
    }
}
