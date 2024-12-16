package com.example.p10roomlocal.ui.theme.view.mahasiswa

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.KeyEventDispatcher.Component
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.p10roomlocal.data.entity.Mahasiswa
import com.example.p10roomlocal.ui.theme.costumwidget.TopAppBar
import com.example.p10roomlocal.ui.theme.viewmodel.DetailMhsViewModel
import com.example.p10roomlocal.ui.theme.viewmodel.DetailUiState
import com.example.p10roomlocal.ui.theme.viewmodel.PenyediaViewModel
import com.example.p10roomlocal.ui.theme.viewmodel.toMahasiswaEntity



@Composable
fun DetailMhsView(
    modifier: Modifier = Modifier,
    viewModel: DetailMhsViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit = {},
    onEditClick: (String) -> Unit = {},
    onDeleteClick: () -> Unit = {},
){
    Scaffold(
        topBar = {
            TopAppBar(
                judul = "Detail Mahasiswa",
                showBackButton = true,
                onBack = onBack,
                modifier = modifier
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEditClick(viewModel.detailUIState.value.detailUiEvent.nim) },
                shape = MaterialTheme.shapes.medium,
                modifier = modifier.padding(16.dp)
            ){
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Mahasiswa",
                )
            }
        }
    ){innerPadding ->
        val detailUiState by viewModel.detailUIState.collectAsState()

        BodyDetailMhs(
            modifier = modifier.padding(innerPadding),
            detailUiState = detailUiState,
            onDeleteClick = {
                viewModel.deleteMhs()
                onDeleteClick
            }
        )
    }
}

@Composable
fun BodyDetailMhs(
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState = DetailUiState(),
    onDeleteClick: () -> Unit = {}
){
    var deleteConfirmationRequared by rememberSaveable { mutableStateOf(false) }
    when{
        detailUiState.isLoading ->{
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator() //tampilkan loading
            }
        }
        detailUiState.isUiEventNotEmpty ->{
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                ItemDetailMhs(
                    mahasiswa = detailUiState.detailUiEvent.toMahasiswaEntity(),
                    modifier = modifier
                )
                Spacer(modifier = modifier.padding(8.dp))
                Button(
                    onClick = {
                        deleteConfirmationRequared = true
                    },
                    modifier = modifier.fillMaxWidth()
                ){
                    Text("Delete")
                }
                if(deleteConfirmationRequared){
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequared = false
                            onDeleteClick()
                        },
                        onDeleteCancel = {
                            deleteConfirmationRequared = false
                        },
                        modifier = modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ItemDetailMhs(
    modifier: Modifier = Modifier,
    mahasiswa: Mahasiswa
){
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ){
        Column(
            modifier = modifier
                .padding(16.dp)
        ) {
            ComponentDetailMhs(judul = "NIM", isinya = mahasiswa.nim)
            Spacer(modifier = modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Nama", isinya = mahasiswa.nama)
            Spacer(modifier = modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Alamat", isinya = mahasiswa.alamat)
            Spacer(modifier = modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Jenis Kelamin", isinya = mahasiswa.jenisKelamin)
            Spacer(modifier = modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Kelas", isinya = mahasiswa.kelas)
            Spacer(modifier = modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Angkatan", isinya = mahasiswa.angkatan)
        }
    }
}

@Composable
fun ComponentDetailMhs(
    modifier: Modifier = Modifier,
    judul:String,
    isinya:String
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(onDismissRequest = {/*Do Nothing*/},
        title = {Text("Delete Data")},
        text = {Text("Apakah anda yakin ingin menghapus data ini?")},
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text("Yes")
            }
        })
}