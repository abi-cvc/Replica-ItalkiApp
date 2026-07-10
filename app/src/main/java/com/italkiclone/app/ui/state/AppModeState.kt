package com.italkiclone.app.ui.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

/**
 * Estado local (no hay auth real) que simula el toggle "Cambiar al modo de
 * profesor / estudiante" visto en las capturas. Gobierna qué bottom nav y
 * qué pantallas se muestran. Se hoist a nivel de AppNavHost y sobrevive
 * rotación gracias a rememberSaveable.
 */
@Composable
fun rememberIsTeacherMode(): MutableState<Boolean> =
    rememberSaveable { mutableStateOf(false) }
