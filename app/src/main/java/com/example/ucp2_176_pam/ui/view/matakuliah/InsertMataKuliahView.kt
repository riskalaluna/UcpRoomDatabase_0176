package com.example.ucp2_176_pam.ui.view.matakuliah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.ucp2_176_pam.ui.navigation.AlamatNavigasi
import com.example.ucp2_176_pam.ui.viewmodel.matakuliah.FormErrorState
import com.example.ucp2_176_pam.ui.viewmodel.matakuliah.MKUIState
import com.example.ucp2_176_pam.ui.viewmodel.matakuliah.MataKuliahEvent
import com.example.ucp2_176_pam.ui.viewmodel.matakuliah.MataKuliahViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2_176_pam.data.entity.Dosen
import com.example.ucp2_176_pam.data.entity.MataKuliah
import com.example.ucp2_176_pam.ui.custumwidget.CstTopAppBar
import com.example.ucp2_176_pam.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.truncate

object DestinasiInsertMK : AlamatNavigasi {
    override val route: String = "insert_mk"
}

//Fungsi ini adalah tampilan utama dari halaman "Tambah Mata Kuliah"
@Composable
fun InsertMKView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MataKuliahViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiStateMataKuliah = viewModel.uiStateMataKuliah
    val snackbarHostState = remember { SnackbarHostState()}
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiStateMataKuliah.snackBarMessage) {
        uiStateMataKuliah.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold (
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },

    ){ padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            CstTopAppBar(
                showBackButton = true,

                judul = "Tambah Mata Kuliah",
                onBack = onBack
            )
            InsertBodyMK(
                uiState = uiStateMataKuliah,
                dosenList = uiStateMataKuliah.dosenList,
                onValueChange = { updateEvent -> viewModel.updateStateMataKuliah(updateEvent) },
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveData()
                        if (viewModel.validataFields()) {
                            viewModel.saveData()
                            delay(500)
                            withContext(Dispatchers.Main) {
                                onNavigate()
                            }
                        }
                    }
                },
            )
        }
    }
}

//berfungsi untuk menyusun form input mata kuliah.
@Composable
fun InsertBodyMK(
    modifier: Modifier = Modifier,
    onValueChange: (MataKuliahEvent) -> Unit,
    uiState: MKUIState,
    dosenList: List<Dosen>,
    onClick: () -> Unit

) {
    Column (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        FormMataKuliah(
            mataKuliahEvent = uiState.mataKuliahEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth(),
            dosenList = dosenList
        )
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF2196F3))
        ) {
            Text("Simpan")
        }
    }
}

//Fungsi ini berisi form input untuk data mata kuliah
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormMataKuliah(
    mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    onValueChange: (MataKuliahEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier,
    dosenList: List<Dosen>,
) {
    val jenis = listOf("MK Wajib", "MK Peminatan")

    var chosenDropdown by remember { mutableStateOf(mataKuliahEvent.dosenPengampu) }
    var expanded by remember { mutableStateOf(false) }


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
            placeholder = { Text("Masukkan nama") }
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
                onValueChange(mataKuliahEvent.copy(semester = it))
            },
            label = { Text("Semester") },
            isError = errorState.semester != null,
            placeholder = { Text("Masukkan Semester") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(text = errorState.semester ?: "", color = Color.Red)



        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jenis MataKuliah", color = Color(0xFF1976D2), fontWeight = FontWeight.Bold)
        Row (
            modifier = Modifier.fillMaxWidth()
        ){
            jenis.forEach { jn ->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = mataKuliahEvent.jenis == jn,
                        onClick = {
                            onValueChange(mataKuliahEvent.copy(jenis = jn))
                        },
                        colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF42A5F5))
                    )
                    Text(
                        text = jn,
                        color = Color(0xFF1976D2)
                    )
                }
            }
        }
        Text(text = errorState.jenis ?: "", color = Color.Red)

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {expanded = !expanded}
        ) {
            OutlinedTextField(
                value = chosenDropdown,
                onValueChange = { },
                label = { Text("Pilih Dosen Pengampu")},
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Expand Menu"
                    )
                },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                readOnly = true,
                isError = errorState.dosenPengampu != null
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                dosenList.forEach { dosen ->
                    DropdownMenuItem(
                        onClick = {
                            chosenDropdown = dosen.nama
                            expanded = false
                            onValueChange(mataKuliahEvent.copy(dosenPengampu = dosen.nama))
                        },
                        text = { Text(text = dosen.nama) },

                        )
                }
            }
        }
        Text(text = errorState.dosenPengampu ?: "", color = Color.Red)

    }
}