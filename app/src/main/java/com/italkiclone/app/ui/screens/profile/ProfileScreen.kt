package com.italkiclone.app.ui.screens.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.italkiclone.app.ui.components.Avatar
import com.italkiclone.app.ui.theme.ItalkiCoral

@Composable
fun ProfileScreen(
    isTeacherMode: Boolean,
    onToggleMode: () -> Unit,
    onNavigateToStudents: () -> Unit,
    onNavigateToClasses: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + slideInVertically(initialOffsetY = { it / 6 }),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Avatar(name = "Carol Velasquez", size = 56)
                Column(modifier = Modifier.padding(start = 12.dp)) {
                    Text("Carol Velasquez", style = MaterialTheme.typography.titleLarge)
                    Text(
                        text = if (isTeacherMode) "Tutor de la comunidad" else "Id.: 9634896",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }

            if (isTeacherMode) {
                ProfileMenuItem(
                    icon = Icons.Filled.Class,
                    label = "Mis clases",
                    onClick = onNavigateToClasses,
                )
                ProfileMenuItem(
                    icon = Icons.Filled.Groups,
                    label = "Mis estudiantes",
                    onClick = onNavigateToStudents,
                )
                ProfileMenuItem(
                    icon = Icons.Filled.AccountBalanceWallet,
                    label = "Mi monedero",
                    onClick = {},
                )
            } else {
                ProfileMenuItem(
                    icon = Icons.Filled.Class,
                    label = "Mis clases",
                    onClick = onNavigateToClasses,
                )
                ProfileMenuItem(
                    icon = Icons.Filled.AccountBalanceWallet,
                    label = "Mi monedero",
                    onClick = {},
                )
            }

            ProfileMenuItem(
                icon = Icons.Filled.SwapHoriz,
                label = if (isTeacherMode) "Cambiar al modo de estudiante" else "Cambiar al modo de profesor",
                onClick = onToggleMode,
                highlighted = true,
            )
        }
    }
}

@Composable
private fun ProfileMenuItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
    highlighted: Boolean = false,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (highlighted) ItalkiCoral else MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = label,
                    modifier = Modifier.padding(start = 12.dp),
                    fontWeight = if (highlighted) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (highlighted) ItalkiCoral else MaterialTheme.colorScheme.onSurface,
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null,
                modifier = Modifier.padding(2.dp),
            )
        }
    }
}
