package com.italkiclone.app.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNavBar(
    isTeacherMode: Boolean,
    currentRoute: String?,
    onNavigate: (Screen) -> Unit,
) {
    val items = if (isTeacherMode) teacherBottomNavItems else studentBottomNavItems
    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = { onNavigate(screen) },
                icon = { Icon(screen.icon, contentDescription = screen.label) },
                label = { Text(screen.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFFF4438),
                    selectedTextColor = Color(0xFFFF4438),
                    indicatorColor = Color(0xFFFFE3E1),
                ),
            )
        }
    }
}
