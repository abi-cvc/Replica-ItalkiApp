package com.italkiclone.app.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RatingStars(rating: Double, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = Color(0xFFF4B400),
            modifier = Modifier.padding(end = 2.dp),
        )
        Text(text = rating.toString(), style = MaterialTheme.typography.labelLarge)
    }
}
