package com.italkiclone.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.italkiclone.app.data.model.ClassStatus
import com.italkiclone.app.ui.theme.ItalkiMint
import com.italkiclone.app.ui.theme.TextPrimary

/** Franja de estado a todo el ancho que se ubica encima de la card, como en el diseño real. */
@Composable
fun StatusBadge(status: ClassStatus, modifier: Modifier = Modifier) {
    val (background, icon) = when (status) {
        ClassStatus.COMPLETED -> ItalkiMint to Icons.Filled.CheckCircle
        ClassStatus.CANCELLED -> Color(0xFFECECEC) to Icons.Filled.Cancel
        ClassStatus.UPCOMING -> Color(0xFFE1F5FE) to Icons.Filled.Event
        ClassStatus.ACTION_REQUIRED -> Color(0xFFFFE3E1) to Icons.Filled.Warning
        ClassStatus.WAITING -> Color(0xFFFFF3D6) to Icons.Filled.Schedule
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(background, RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(icon, contentDescription = null, tint = TextPrimary, modifier = Modifier.size(16.dp))
        Text(
            text = status.label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary,
            modifier = Modifier.padding(start = 6.dp),
        )
    }
}
