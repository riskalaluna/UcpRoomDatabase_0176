package com.example.ucp2_176_pam.ui.view.dosen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ucp2_176_pam.data.entity.Dosen
import com.example.ucp2_176_pam.ui.viewmodel.dosen.DetailDsnViewModel
import com.example.ucp2_176_pam.ui.viewmodel.dosen.DetailUiState
import com.example.ucp2_176_pam.ui.viewmodel.dosen.toDosenEntity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2_176_pam.ui.custumwidget.CstTopAppBar
import com.example.ucp2_176_pam.ui.viewmodel.PenyediaViewModel

@Composable
fun DetailDsnView (
    modifier: Modifier = Modifier,
    viewModel: DetailDsnViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit = { }
) {
    Scaffold (
        topBar = {
            CstTopAppBar(
                judul = "Detail Dosen",
                showBackButton = true,
                onBack = onBack,
                modifier = modifier
            )
        }
    ) { innerPadding ->
        val detailUiState by viewModel.detailUiState.collectAsState()

        BodyDetailDsn(
            modifier = Modifier.padding(innerPadding),
            detailUiState = detailUiState
        )
    }
}



@Composable
fun BodyDetailDsn(
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState = DetailUiState()
) {
    when {
        detailUiState.isLoading -> {
            Box (
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator() //Tampilkan loading
            }
        }
        detailUiState.isUiEventNotEmpty -> {
            Column (
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailDsn(
                    dosen = detailUiState.detailUiEvent.toDosenEntity(),
                    modifier = Modifier
                )
            }
        }

        detailUiState.isUiEventEmpty -> {
            Box (
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data tidak ditemukan",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}


@Composable
fun ItemDetailDsn (
    modifier: Modifier = Modifier,
    dosen: Dosen
) {
    Card (
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column (
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailDsn(judul = "NIDN", isinya = dosen.nidn)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailDsn(judul = "Nama", isinya = dosen.nama)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailDsn(judul = "Jenis Kelamin", isinya = dosen.jenisKelamin)
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun ComponentDetailDsn (
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Column (
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
            fontWeight = FontWeight.Bold,
        )
    }
}