package com.italkiclone.app.ui.screens.chats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.italkiclone.app.data.model.ChatConversation
import com.italkiclone.app.ui.components.Avatar
import com.italkiclone.app.ui.theme.ItalkiCoral

@Composable
fun ChatItem(chat: ChatConversation, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Avatar(name = chat.senderName, size = 48)
        Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
            Text(
                text = chat.senderName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = if (chat.isUnread) FontWeight.Bold else FontWeight.Normal,
            )
            Text(
                text = chat.lastMessage,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Text(
            text = chat.dateLabel,
            style = MaterialTheme.typography.bodySmall,
            color = if (chat.isUnread) ItalkiCoral else MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
