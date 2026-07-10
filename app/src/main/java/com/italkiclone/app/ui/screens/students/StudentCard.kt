package com.italkiclone.app.ui.screens.students

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.italkiclone.app.data.model.LanguageCompetency
import com.italkiclone.app.data.model.Student
import com.italkiclone.app.data.model.StudentRelation
import com.italkiclone.app.ui.components.Avatar
import com.italkiclone.app.ui.theme.ItalkiGreenAccent
import com.italkiclone.app.ui.theme.ItalkiTeal
import com.italkiclone.app.ui.theme.TextSecondary

@Composable
fun StudentCard(student: Student, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Avatar(name = student.name, size = 44)
                    Column(modifier = Modifier.padding(start = 12.dp)) {
                        Text(
                            text = "${student.countryFlagEmoji} ${student.name}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
                Icon(Icons.Filled.MoreVert, contentDescription = "Más opciones", tint = TextSecondary)
            }

            if (student.relation == StudentRelation.CURRENT) {
                CurrentStudentDetails(student)
            } else {
                PotentialStudentDetails(student)
            }
        }
    }
}

@Composable
private fun CurrentStudentDetails(student: Student) {
    Column(modifier = Modifier.padding(start = 56.dp, top = 4.dp)) {
        Text(text = "Última clase ${student.lastClassLabel}", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
        Text(
            text = "Habla: ${student.languagesSpoken.joinToString(", ")}",
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary,
        )
        Text(
            text = "Lecciones totales · ${student.totalLessons}",
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary,
        )
    }
}

@Composable
private fun PotentialStudentDetails(student: Student) {
    Column(modifier = Modifier.padding(start = 56.dp, top = 4.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Column {
            Text(text = "Posible idioma de aprendizaje", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = student.possibleLearningLanguage,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                ProficiencyBar(level = student.possibleLanguageLevel, modifier = Modifier.padding(start = 8.dp))
            }
        }

        if (student.profileTags.isNotEmpty()) {
            Text(
                text = student.profileTags.joinToString(";"),
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
            )
        }

        Column {
            Text(text = "Competencias lingüísticas", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
            student.languageCompetencies.forEach { competency ->
                CompetencyRow(competency)
            }
        }
    }
}

@Composable
private fun CompetencyRow(competency: LanguageCompetency) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 2.dp)) {
        Text(text = competency.language, style = MaterialTheme.typography.bodyMedium)
        if (competency.isNative) {
            Text(
                text = "Nativo",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = ItalkiTeal,
                modifier = Modifier.padding(start = 8.dp),
            )
        } else {
            ProficiencyBar(level = competency.proficiencyLevel, modifier = Modifier.padding(start = 8.dp))
        }
    }
}

@Composable
private fun ProficiencyBar(level: Int, modifier: Modifier = Modifier, segments: Int = 5) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        repeat(segments) { index ->
            val filled = index < level
            Column(
                modifier = Modifier
                    .size(width = 10.dp, height = 6.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(if (filled) ItalkiGreenAccent else MaterialTheme.colorScheme.surfaceVariant),
            ) {}
        }
    }
}
