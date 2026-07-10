package com.italkiclone.app.ui.screens.teachers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.italkiclone.app.data.model.Teacher
import com.italkiclone.app.ui.components.Avatar
import com.italkiclone.app.ui.components.RatingStars
import com.italkiclone.app.ui.theme.ItalkiCoral

@Composable
fun TeacherCard(teacher: Teacher, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Box {
            AsyncImage(
                model = "https://picsum.photos/seed/${teacher.photoSeed}/400/220",
                contentDescription = teacher.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
            )
            if (teacher.discountPercent != null) {
                Text(
                    text = "${teacher.discountPercent} % de descuento",
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(ItalkiCoral, RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                )
            }
            Text(
                text = if (teacher.isProfessional) "Profesor profesional" else "Tutor de la comunidad",
                color = Color.White,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
                    .background(Color.Black.copy(alpha = 0.45f), RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
            )
        }

        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Avatar(name = teacher.name, size = 32)
                    Text(
                        text = "${teacher.countryFlagEmoji} ${teacher.name}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }
                Text(
                    text = "${teacher.teachingLanguage} Nativo",
                    style = MaterialTheme.typography.labelMedium,
                    color = ItalkiCoral,
                )
            }

            Text(
                text = teacher.bio,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 6.dp),
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "USD ${"%.2f".format(teacher.pricePerHour)}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = " / hora",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(bottom = 2.dp),
                    )
                }
                if (teacher.totalClasses > 0) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RatingStars(rating = teacher.rating)
                        Text(
                            text = " · ${teacher.totalClasses} clases",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                } else {
                    Text(text = "0 clases · NEW", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
