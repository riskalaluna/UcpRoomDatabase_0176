package com.example.ucp2_176_pam.ui.view.matakuliah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.ucp2_176_pam.ui.navigation.AlamatNavigasi
import com.example.ucp2_176_pam.ui.viewmodel.matakuliah.FormErrorState
import com.example.ucp2_176_pam.ui.viewmodel.matakuliah.MKUIState
import com.example.ucp2_176_pam.ui.viewmodel.matakuliah.MataKuliahEvent
import com.example.ucp2_176_pam.ui.viewmodel.matakuliah.MataKuliahViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2_176_pam.ui.viewmodel.PenyediaViewModel

object DestinasiInsert : AlamatNavigasi {
    override val route: String = "insert_mk"
}

@Composable
fun InsertMKView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MataKuliahViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState()}
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.resetSnackBarMessage()
        }
    }
}

@Composable
fun InsertBodyMK(
    modifier: Modifier = Modifier,
    onValueChange: (MataKuliahEvent) -> Unit,
    uiState: MKUIState,
    onClick: () -> Unit

) {
    Column (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        FormMataKuliah(
            mataKuliahEvent = uiState.matakuliahEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simpan")
        }
    }
}

@Composable
fun FormMataKuliah(
    mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    onValueChange: (MataKuliahEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {

    Column (
        modifier = modifier.fillMaxWidth()
    ){

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.nama,
            onValueChange = {
                onValueChange(mataKuliahEvent.copy(nama = it))
            },
            label = { Text("Nama") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan nama") },
        )
        Text(text = errorState.nama ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.kode, onValueChange = {
                onValueChange(mataKuliahEvent.copy(kode = it))
            },
            label = { Text("KODE") },
            isError = errorState.kode != null,
            placeholder = { Text("Masukkan KODE") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(text = errorState.kode ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.sks, onValueChange = {
                onValueChange(mataKuliahEvent.copy(sks = it))
            },
            label = { Text("SKS") },
            isError = errorState.sks != null,
            placeholder = { Text("Masukkan SKS") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(text = errorState.sks ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.semester, onValueChange = {
                onValueChange(mataKuliahEvent.copy(sks = it))
            },
            label = { Text("Semester") },
            isError = errorState.semester!= null,
            placeholder = { Text("Masukkan Semester") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(text = errorState.semester ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.jenis, onValueChange = {
                onValueChange(mataKuliahEvent.copy(jenis = it))
            },
            label = { Text("Jenis") },
            isError = errorState.jenis != null,
            placeholder = { Text("Masukkan Jenis") },
        )
        Text(text = errorState.jenis ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.dosenPengampu, onValueChange = {
                onValueChange(mataKuliahEvent.copy(dosenPengampu = it))
            },
            label = { Text("DosenPengampu") },
            isError = errorState.dosenPengampu != null,
            placeholder = { Text("Masukkan Dosen Pengampu") },
        )
        Text(text = errorState.dosenPengampu ?: "", color = Color.Red)
    }
}