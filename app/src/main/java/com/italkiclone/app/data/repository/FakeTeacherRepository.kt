package com.italkiclone.app.data.repository

import com.italkiclone.app.data.fake.FakeDataGenerator
import com.italkiclone.app.data.model.Teacher
import com.italkiclone.app.domain.repository.TeacherRepository
import kotlinx.coroutines.delay

class FakeTeacherRepository : TeacherRepository {
    private val teachers by lazy { FakeDataGenerator.generateTeachers() }

    override suspend fun getTeachers(): List<Teacher> {
        delay(400)
        return teachers
    }
}
