package com.italkiclone.app.domain.repository

import com.italkiclone.app.data.model.ChatConversation

interface ChatRepository {
    suspend fun getChats(): List<ChatConversation>
}
