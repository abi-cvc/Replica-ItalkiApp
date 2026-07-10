package com.italkiclone.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.italkiclone.app.data.model.ClassStatus
import com.italkiclone.app.ui.theme.ItalkiCoralDark
import com.italkiclone.app.ui.theme.ItalkiGreenAccent
import com.italkiclone.app.ui.theme.ItalkiMint
import com.italkiclone.app.ui.theme.ItalkiTeal

@Composable
fun StatusBadge(status: ClassStatus, modifier: Modifier = Modifier) {
    val (background, content) = when (status) {
        ClassStatus.COMPLETED -> ItalkiMint to ItalkiGreenAccent
        ClassStatus.CANCELLED -> Color(0xFFECECEC) to Color(0xFF8A8A94)
        ClassStatus.UPCOMING -> Color(0xFFE1F5FE) to ItalkiTeal
        ClassStatus.ACTION_REQUIRED -> Color(0xFFFFE3E1) to ItalkiCoralDark
        ClassStatus.WAITING -> Color(0xFFFFF3D6) to Color(0xFFB8860B)
    }
    Row(
        modifier = modifier
            .background(background, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        Text(text = status.label, style = MaterialTheme.typography.labelMedium, color = content)
    }
}
