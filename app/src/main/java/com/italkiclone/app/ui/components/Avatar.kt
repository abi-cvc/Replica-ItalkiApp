package com.italkiclone.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.italkiclone.app.ui.theme.AvatarPalette
import kotlin.math.absoluteValue

/** Avatar con foto (Coil) o, si no hay imageUrl, iniciales sobre un color determinístico
 * a partir del nombre — tal como se ve en Chats/Estudiantes en las capturas originales. */
@Composable
fun Avatar(
    name: String,
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    size: Int = 48,
) {
    if (imageUrl != null) {
        AsyncImage(
            model = imageUrl,
            contentDescription = name,
            modifier = modifier
                .size(size.dp)
                .clip(CircleShape),
        )
    } else {
        val color = AvatarPalette[name.hashCode().absoluteValue % AvatarPalette.size]
        Box(
            modifier = modifier
                .size(size.dp)
                .clip(CircleShape)
                .background(color),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = name.trim().firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}
