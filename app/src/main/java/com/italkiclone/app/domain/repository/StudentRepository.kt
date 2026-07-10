package com.italkiclone.app.domain.repository

import com.italkiclone.app.data.model.Student

interface StudentRepository {
    suspend fun getStudents(): List<Student>
}
