package com.italkiclone.app.data.model

data class ChatConversation(
    val id: String,
    val senderName: String,
    val lastMessage: String,
    val dateLabel: String,
    val isUnread: Boolean,
)
