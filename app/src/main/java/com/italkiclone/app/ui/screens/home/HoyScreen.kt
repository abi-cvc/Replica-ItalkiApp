package com.italkiclone.app.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/** Pantalla "Hoy": no es una de las 3 listas obligatorias del taller, se deja
 * como resumen simple para no dejar la tab vacía. */
@Composable
fun HoyScreen(isTeacherMode: Boolean, modifier: Modifier = Modifier) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + slideInVertically(initialOffsetY = { -it / 6 }),
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
        ) {
            Text(
                text = if (isTeacherMode) "Hola, Carol Velasquez" else "Hoy",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = if (isTeacherMode) {
                    "Panel de profesor: revisa tus clases y estudiantes desde \"Yo\"."
                } else {
                    "¿Cómo puedo ayudarte? Encuentra profesores, revisa tus clases o chatea."
                },
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}
