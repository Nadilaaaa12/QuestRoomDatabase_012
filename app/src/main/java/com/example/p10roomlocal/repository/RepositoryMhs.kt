package com.example.p10roomlocal.repository

import com.example.p10roomlocal.data.entity.Mahasiswa

interface RepositoryMhs {
    suspend fun insertMhs(mahasiswa: Mahasiswa)
}