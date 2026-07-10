package com.italkiclone.app.data.repository

import com.italkiclone.app.data.fake.FakeDataGenerator
import com.italkiclone.app.data.model.ChatConversation
import com.italkiclone.app.domain.repository.ChatRepository
import kotlinx.coroutines.delay

class FakeChatRepository : ChatRepository {
    private val chats by lazy { FakeDataGenerator.generateChats() }

    override suspend fun getChats(): List<ChatConversation> {
        delay(400)
        return chats
    }
}
