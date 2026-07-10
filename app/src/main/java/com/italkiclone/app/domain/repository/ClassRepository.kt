package com.italkiclone.app.domain.repository

import com.italkiclone.app.data.model.ClassSession

interface ClassRepository {
    suspend fun getClasses(): List<ClassSession>
}
