package com.italkiclone.app.data.repository

import com.italkiclone.app.data.fake.FakeDataGenerator
import com.italkiclone.app.data.model.ClassSession
import com.italkiclone.app.domain.repository.ClassRepository
import kotlinx.coroutines.delay

class FakeClassRepository : ClassRepository {
    private val classes by lazy { FakeDataGenerator.generateClasses() }

    override suspend fun getClasses(): List<ClassSession> {
        delay(400)
        return classes
    }
}
