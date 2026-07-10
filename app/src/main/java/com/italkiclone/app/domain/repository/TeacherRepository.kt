package com.italkiclone.app.domain.repository

import com.italkiclone.app.data.model.Teacher

interface TeacherRepository {
    suspend fun getTeachers(): List<Teacher>
}
