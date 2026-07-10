package com.italkiclone.app.ui.screens.teachers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.italkiclone.app.ui.components.LoadingSkeletonList
import com.italkiclone.app.ui.state.UiState
import com.italkiclone.app.ui.theme.TextSecondary

private enum class TeacherTypeFilter(val label: String) {
    ALL("Todos"),
    PROFESSIONAL("Profesional"),
    COMMUNITY("Comunidad"),
}

@Composable
fun TeachersListScreen(
    modifier: Modifier = Modifier,
    viewModel: TeachersViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    var query by remember { mutableStateOf("") }
    var selectedLanguage by remember { mutableStateOf<String?>(null) }
    var selectedType by remember { mutableStateOf(TeacherTypeFilter.ALL) }

    Column(modifier = modifier.fillMaxSize()) {
        when (val current = state) {
            is UiState.Loading -> LoadingSkeletonList(modifier = Modifier.fillMaxSize())
            is UiState.Success -> {
                val allTeachers = current.data
                val languages = remember(allTeachers) {
                    allTeachers.map { it.teachingLanguage }.distinct().sorted()
                }

                TeacherFilterBar(
                    query = query,
                    onQueryChange = { query = it },
                    languages = languages,
                    selectedLanguage = selectedLanguage,
                    onLanguageSelected = { selectedLanguage = it },
                    selectedType = selectedType,
                    onTypeSelected = { selectedType = it },
                )

                val filtered = allTeachers.filter { teacher ->
                    (query.isBlank() || teacher.name.contains(query, ignoreCase = true)) &&
                        (selectedLanguage == null || teacher.teachingLanguage == selectedLanguage) &&
                        when (selectedType) {
                            TeacherTypeFilter.ALL -> true
                            TeacherTypeFilter.PROFESSIONAL -> teacher.isProfessional
                            TeacherTypeFilter.COMMUNITY -> !teacher.isProfessional
                        }
                }

                if (filtered.isEmpty()) {
                    EmptyFilterState(modifier = Modifier.fillMaxSize())
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                    ) {
                        itemsIndexed(filtered, key = { _, teacher -> teacher.id }) { _, teacher ->
                            TeacherCard(
                                teacher = teacher,
                                modifier = Modifier.animateItem(),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TeacherFilterBar(
    query: String,
    onQueryChange: (String) -> Unit,
    languages: List<String>,
    selectedLanguage: String?,
    onLanguageSelected: (String?) -> Unit,
    selectedType: TeacherTypeFilter,
    onTypeSelected: (TeacherTypeFilter) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar profesor") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(24.dp),
        )
        Row(
            modifier = Modifier.padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            LanguageFilterChip(
                languages = languages,
                selected = selectedLanguage,
                onSelected = onLanguageSelected,
            )
            TypeFilterChip(selected = selectedType, onSelected = onTypeSelected)
        }
    }
}

@Composable
private fun LanguageFilterChip(
    languages: List<String>,
    selected: String?,
    onSelected: (String?) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        FilterChip(
            selected = selected != null,
            onClick = { expanded = true },
            label = { Text(selected ?: "Idioma") },
            trailingIcon = { Icon(Icons.Filled.ArrowDropDown, contentDescription = null) },
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { Text("Todos") }, onClick = { onSelected(null); expanded = false })
            languages.forEach { language ->
                DropdownMenuItem(text = { Text(language) }, onClick = { onSelected(language); expanded = false })
            }
        }
    }
}

@Composable
private fun TypeFilterChip(
    selected: TeacherTypeFilter,
    onSelected: (TeacherTypeFilter) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        FilterChip(
            selected = selected != TeacherTypeFilter.ALL,
            onClick = { expanded = true },
            label = { Text(selected.label) },
            trailingIcon = { Icon(Icons.Filled.ArrowDropDown, contentDescription = null) },
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            TeacherTypeFilter.entries.forEach { type ->
                DropdownMenuItem(text = { Text(type.label) }, onClick = { onSelected(type); expanded = false })
            }
        }
    }
}

@Composable
private fun EmptyFilterState(modifier: Modifier = Modifier) {
    Box(modifier = modifier.padding(32.dp), contentAlignment = Alignment.Center) {
        Text(
            text = "No se encontraron profesores con esos filtros",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            textAlign = TextAlign.Center,
        )
    }
}
