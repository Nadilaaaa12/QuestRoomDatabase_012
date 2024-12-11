package com.example.p10roomlocal.dependeciesinjection

import android.content.Context
import com.example.p10roomlocal.data.database.KrsDatabase
import com.example.p10roomlocal.repository.LocalRepositoryMhs
import com.example.p10roomlocal.repository.RepositoryMhs

interface InterfaceContainerApp {
    val repositoryMhs: RepositoryMhs
}

class ContainerApp(private val context: Context) : InterfaceContainerApp {
    override val repositoryMhs: RepositoryMhs by lazy {
        LocalRepositoryMhs(KrsDatabase.getDatabase(context).mahasiswaDao())
    }
}
