package com.italkiclone.app.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.italkiclone.app.data.model.ClassSession
import com.italkiclone.app.ui.components.Avatar
import com.italkiclone.app.ui.state.UiState
import com.italkiclone.app.ui.theme.ItalkiCoral
import com.italkiclone.app.ui.theme.TextSecondary

/**
 * Pantalla "Hoy". No es una de las 3 listas obligatorias del taller.
 *
 * Mejora de Fase C: en la app real, "Hoy" del estudiante abre con contenido
 * promocional (test de tipo de estudiante, calendario genérico, anuncio de
 * italki Plus) sin ningún elemento accionable — a diferencia de "Hoy" del
 * profesor, que sí antepone información funcional (ganancias, clases que
 * requieren acción). Aquí se antepone la próxima clase del estudiante (o un
 * CTA para buscar profesor si no tiene ninguna) antes de cualquier otro
 * contenido, reutilizando las listas de Clases/Profesores ya construidas.
 * El panel de profesor replica la estructura real (ganancias, mini-dashboard
 * de acción, estadísticas generales), derivando todo de las mismas
 * [ClassSession] que alimentan la lista de Clases.
 */
@Composable
fun HoyScreen(
    isTeacherMode: Boolean,
    modifier: Modifier = Modifier,
    onNavigateToClasses: () -> Unit = {},
    onNavigateToProfesores: () -> Unit = {},
    viewModel: HoyViewModel = viewModel(),
) {
    val nextClassState by viewModel.nextClass.collectAsState()
    val teacherStatsState by viewModel.teacherStats.collectAsState()

    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + slideInVertically(initialOffsetY = { -it / 6 }),
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = if (isTeacherMode) "Hola, Carol Velasquez" else "Hoy",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )

            if (isTeacherMode) {
                when (val current = teacherStatsState) {
                    is UiState.Loading -> Unit
                    is UiState.Success -> TeacherHomeDashboard(
                        stats = current.data,
                        onViewClasses = onNavigateToClasses,
                    )
                }
            } else {
                when (val current = nextClassState) {
                    is UiState.Loading -> Unit
                    is UiState.Success -> NextClassSection(
                        nextClass = current.data,
                        onViewClasses = onNavigateToClasses,
                        onFindTeacher = onNavigateToProfesores,
                    )
                }
            }
        }
    }
}

@Composable
private fun TeacherHomeDashboard(
    stats: TeacherHomeStats,
    onViewClasses: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        DashboardCard {
            Text(text = "Ganancias", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(
                text = "Saldo total",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                modifier = Modifier.padding(top = 8.dp),
            )
            Text(
                text = "USD ${"%.2f".format(stats.balanceUsd)}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
        }

        DashboardCard(onClick = onViewClasses) {
            StatsRow(
                listOf(
                    stats.upcomingCount.toString() to "Próxima",
                    stats.actionRequiredCount.toString() to "Acción necesaria",
                    stats.packageActionCount.toString() to "Acción sobre el paquete",
                ),
            )
        }

        DashboardCard {
            Text(text = "Estadísticas generales", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            StatsRow(
                modifier = Modifier.padding(top = 8.dp),
                items = listOf(
                    stats.completedClassesCount.toString() to "Clases completadas",
                    "${stats.responseRatePercent}%" to "Respuesta",
                    "${stats.attendanceRatePercent}%" to "Asistencia",
                ),
            )
        }
    }
}

@Composable
private fun DashboardCard(
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .let { if (onClick != null) it.clickable(onClick = onClick) else it },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp), content = content)
    }
}

@Composable
private fun StatsRow(items: List<Pair<String, String>>, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth()) {
        items.forEach { (value, label) ->
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun NextClassSection(
    nextClass: ClassSession?,
    onViewClasses: () -> Unit,
    onFindTeacher: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (nextClass == null) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable(onClick = onFindTeacher),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Aún no tienes clases próximas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = "Encuentra un profesor y agenda tu próxima clase",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp),
                )
            }
        }
        return
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Tu próxima clase",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .clickable(onClick = onViewClasses),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Avatar(name = nextClass.participantName, size = 44)
                    Column(modifier = Modifier.padding(start = 12.dp)) {
                        Text(
                            text = "${nextClass.participantName} · ${nextClass.classTitle}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            text = "${nextClass.dateTimeLabel} · ${nextClass.durationMinutes} min",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "Ver mis clases",
                    tint = ItalkiCoral,
                    modifier = Modifier.padding(2.dp),
                )
            }
        }
    }
}
