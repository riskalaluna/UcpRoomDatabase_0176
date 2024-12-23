package com.example.ucp2_176_pam.ui.viewmodel.matakuliah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2_176_pam.data.entity.Dosen
import com.example.ucp2_176_pam.data.entity.MataKuliah
import com.example.ucp2_176_pam.repository.RepositoryDsn
import com.example.ucp2_176_pam.repository.RepositoryMK
import com.example.ucp2_176_pam.ui.navigation.DestinasiUpdate
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

//digunakan untuk memperbarui atau mengedit data mata kuliah
class UpdateMataKuliahViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMK: RepositoryMK,
    private val repositoryDsn: RepositoryDsn
) : ViewModel() {

    var dosenList by mutableStateOf<List<Dosen>>(emptyList())
        private set
    var updateUIState by mutableStateOf(MKUIState())
        private set

    private val _kode: String = checkNotNull(savedStateHandle[DestinasiUpdate.KODE])

    init {
        viewModelScope.launch {
            updateUIState = repositoryMK.getMK(_kode)
                .filterNotNull()
                .first()
                .toUIStateMK()
        }

        viewModelScope.launch {
            val dosenListFromRepo = repositoryDsn.getAllDsn().first()
            dosenList = dosenListFromRepo
            updateUIState = updateUIState.copy(dosenList = dosenList)
        }
    }

    fun updateState (mataKuliahEvent: MataKuliahEvent) {
        updateUIState = updateUIState.copy(
            mataKuliahEvent = mataKuliahEvent,
        )
    }

    fun validateFields(): Boolean {
        val event = updateUIState.mataKuliahEvent
        val errorState = FormErrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong",
        )

        updateUIState = updateUIState.copy( isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData() {
        val currentEvent = updateUIState.mataKuliahEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMK.updateMK(currentEvent.toMataKuliahEntity())
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data berhasil diupdate",
                        mataKuliahEvent = MataKuliahEvent(),
                        isEntryValid = FormErrorState()
                    )
                    println("snackbarMessage diatur: ${updateUIState.snackBarMessage}")
                } catch (e: Exception) {
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data gagal diupdate"
                    )
                }
            }
        } else {
            updateUIState = updateUIState.copy(
                snackBarMessage = "Data gagal diupdate"
            )
        }
    }
    fun resetSnackBarMessage() {
        updateUIState = updateUIState.copy(snackBarMessage = null)
    }
}

fun MataKuliah.toUIStateMK(): MKUIState = MKUIState(
    mataKuliahEvent = this.toDetailUiEvent(),
)