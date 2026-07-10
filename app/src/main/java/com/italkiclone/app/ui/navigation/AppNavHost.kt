package com.italkiclone.app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.italkiclone.app.ui.screens.chats.ChatsListScreen
import com.italkiclone.app.ui.screens.classes.ClassesListScreen
import com.italkiclone.app.ui.screens.home.HoyScreen
import com.italkiclone.app.ui.screens.profile.ProfileScreen
import com.italkiclone.app.ui.screens.students.StudentsListScreen
import com.italkiclone.app.ui.screens.teachers.TeachersListScreen
import com.italkiclone.app.ui.state.rememberIsTeacherMode

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    var isTeacherMode by rememberIsTeacherMode()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.hierarchy?.firstOrNull()?.route

    Scaffold(
        bottomBar = {
            BottomNavBar(
                isTeacherMode = isTeacherMode,
                currentRoute = currentRoute,
                onNavigate = { screen ->
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Hoy.route,
            modifier = androidx.compose.ui.Modifier.padding(innerPadding),
        ) {
            composable(Screen.Hoy.route) { HoyScreen(isTeacherMode = isTeacherMode) }
            composable(Screen.Profesores.route) { TeachersListScreen() }
            composable(Screen.Chats.route) { ChatsListScreen() }
            composable(Screen.Clases.route) { ClassesListScreen() }
            composable(Screen.Estudiantes.route) { StudentsListScreen() }
            composable(Screen.Yo.route) {
                ProfileScreen(
                    isTeacherMode = isTeacherMode,
                    onToggleMode = { isTeacherMode = !isTeacherMode },
                    onNavigateToStudents = { navController.navigate(Screen.Estudiantes.route) },
                    onNavigateToClasses = { navController.navigate(Screen.Clases.route) },
                )
            }
        }
    }
}
