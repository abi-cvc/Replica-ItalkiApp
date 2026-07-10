package com.italkiclone.app.data.repository

import com.italkiclone.app.data.fake.FakeDataGenerator
import com.italkiclone.app.data.model.Student
import com.italkiclone.app.domain.repository.StudentRepository
import kotlinx.coroutines.delay

class FakeStudentRepository : StudentRepository {
    private val students by lazy { FakeDataGenerator.generateStudents() }

    override suspend fun getStudents(): List<Student> {
        delay(400)
        return students
    }
}
