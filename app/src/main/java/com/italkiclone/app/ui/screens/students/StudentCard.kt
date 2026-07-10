package com.italkiclone.app.ui.screens.students

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.italkiclone.app.data.model.Student
import com.italkiclone.app.ui.components.Avatar

@Composable
fun StudentCard(student: Student, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Avatar(name = student.name, size = 44)
            Column(modifier = Modifier.padding(start = 12.dp)) {
                Text(
                    text = "${student.countryFlagEmoji} ${student.name}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = "Última clase ${student.lastClassLabel}",
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = "Habla: ${student.languagesSpoken.joinToString(", ")}",
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = "Lecciones totales · ${student.totalLessons}",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}
