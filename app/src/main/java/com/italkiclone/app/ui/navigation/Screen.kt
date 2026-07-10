package com.italkiclone.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    data object Hoy : Screen("hoy", "Hoy", Icons.Filled.Home)
    data object Profesores : Screen("profesores", "Profesores", Icons.Filled.School)
    data object Chats : Screen("chats", "Chats", Icons.AutoMirrored.Filled.Chat)
    data object Clases : Screen("clases", "Clases", Icons.Filled.Class)
    data object Yo : Screen("yo", "Yo", Icons.Filled.Person)

    // No es un tab del bottom nav: se llega push-navegando desde "Yo" en modo profesor.
    data object Estudiantes : Screen("estudiantes", "Mis estudiantes", Icons.Filled.School)
}

val studentBottomNavItems = listOf(Screen.Hoy, Screen.Profesores, Screen.Chats, Screen.Clases, Screen.Yo)
val teacherBottomNavItems = listOf(Screen.Hoy, Screen.Chats, Screen.Yo)
