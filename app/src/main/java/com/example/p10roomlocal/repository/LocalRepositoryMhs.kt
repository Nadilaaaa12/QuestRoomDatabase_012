package com.example.p10roomlocal.repository

import com.example.p10roomlocal.data.dao.MahasiswaDao
import com.example.p10roomlocal.data.entity.Mahasiswa

class LocalRepositoryMhs(
    private val mahasiswaDao: MahasiswaDao)
    : RepositoryMhs {

        override suspend fun insertMhs(mahasiswa: Mahasiswa) {
            mahasiswaDao.insertMahasiswa(mahasiswa)
        }
    }
