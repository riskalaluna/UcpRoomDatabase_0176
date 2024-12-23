package com.example.ucp2_176_pam.ui.view.matakuliah

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2_176_pam.ui.custumwidget.CstTopAppBar
import com.example.ucp2_176_pam.ui.viewmodel.PenyediaViewModel
import com.example.ucp2_176_pam.ui.viewmodel.matakuliah.UpdateMataKuliahViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//fungsi untuk meng edit data yang ada
@Composable
fun UpdateMataKuliahView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateMataKuliahViewModel = viewModel(factory = PenyediaViewModel.Factory) //Inisialisasi ViewModel
) {
    val uiStateMataKuliah = viewModel.updateUIState
    val snackbarHostState = remember { SnackbarHostState() } //snackbar state
    val coroutineScope = rememberCoroutineScope()

    //obeservasi perubahan snackbarMessage
    LaunchedEffect(uiStateMataKuliah.snackBarMessage) {
        println("LaunchedEffect triggered")
        uiStateMataKuliah.snackBarMessage?.let { message ->
            println("Snackbar message received: $message")
            coroutineScope.launch {
                println("Launching coroutine for snackbar")
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long
                )
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold (
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },

        topBar = {
            CstTopAppBar(
                judul ="Edit Mata Kuliah",
                showBackButton = true,
                onBack = onBack,
            )
        }
    ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ){

            InsertBodyMK(
                uiState = uiStateMataKuliah,
                onValueChange = { updatedEvent -> viewModel.updateState(updatedEvent) //update state di viewmodel
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateFields()) {
                            viewModel.updateData()
                            delay(600)
                            withContext(Dispatchers.Main) {
                                onNavigate()
                            }
                        }
                    }
                },
                dosenList = uiStateMataKuliah.dosenList
            )
        }
    }
}