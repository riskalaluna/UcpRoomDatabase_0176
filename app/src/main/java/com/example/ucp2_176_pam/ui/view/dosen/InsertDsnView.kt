package com.example.ucp2_176_pam.ui.view.dosen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2_176_pam.ui.custumwidget.CstTopAppBar
import com.example.ucp2_176_pam.ui.navigation.AlamatNavigasi
import com.example.ucp2_176_pam.ui.viewmodel.PenyediaViewModel
import com.example.ucp2_176_pam.ui.viewmodel.dosen.DosenEvent
import com.example.ucp2_176_pam.ui.viewmodel.dosen.DosenViewModel
import com.example.ucp2_176_pam.ui.viewmodel.dosen.DsnUIState
import com.example.ucp2_176_pam.ui.viewmodel.dosen.FormErrorState
import kotlinx.coroutines.launch

object DestinasiInsertDsn : AlamatNavigasi {
    override val route: String = "insert_dsn"
}

@Composable
fun InsertDsnView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DosenViewModel = viewModel(factory = PenyediaViewModel.Factory) //
) {
    val uiState = viewModel.uiState //ambil ui state viewmodel
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }
    Scaffold (
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ){

            CstTopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Dosen"
            )
            //Isi Body
            InsertBodyDsn(
                uiState = uiState,
                onValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveData()
                    }
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun InsertBodyDsn(
    modifier: Modifier = Modifier,
    onValueChange: (DosenEvent) -> Unit,
    uiState: DsnUIState,
    onClick: () -> Unit

) {
    Column (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        FormDosen(
            dosenEvent = uiState.dosenEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF42A5F5))
        ) {
            Text("Simpan", color = Color.White)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormDosen(
    dosenEvent: DosenEvent = DosenEvent(),
    onValueChange: (DosenEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {
    val jenisKelamin = listOf("laki-laki", "perempuan")

    Column (
        modifier = modifier.fillMaxWidth()
    ){

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dosenEvent.nama,
            onValueChange = {
                onValueChange(dosenEvent.copy(nama = it))
            },
            label = { Text("Nama") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan nama") },
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Color(0xFF42A5F5),
                focusedBorderColor = Color(0xFF42A5F5),
            )
        )
        Text(text = errorState.nama ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dosenEvent.nidn, onValueChange = {
                onValueChange(dosenEvent.copy(nidn = it))
            },
            label = { Text("NIDN") },
            isError = errorState.nidn != null,
            placeholder = { Text("Masukkan NIDN") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF42A5F5),
                cursorColor = Color(0xFF42A5F5)
            )
        )
        Text(text = errorState.nidn ?: "", color = Color.Red)

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jenis Kelamin", color = Color(0xFF1976D2), fontWeight = FontWeight.Bold)
        Row (
            modifier = Modifier.fillMaxWidth()
        ){
            jenisKelamin.forEach { jk ->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = dosenEvent.jenisKelamin == jk,
                        onClick = {
                            onValueChange(dosenEvent.copy(jenisKelamin = jk))
                        },
                        colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF42A5F5))
                    )
                    Text(
                        text = jk,
                        color = Color(0xFF1976D2)
                    )
                }
            }
        }
        Text(text = errorState.jenisKelamin ?: "", color = Color.Red)
    }
}
