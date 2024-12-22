package com.example.ucp2_176_pam.ui.viewmodel.matakuliah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2_176_pam.data.entity.MataKuliah
import com.example.ucp2_176_pam.repository.RepositoryMK
import kotlinx.coroutines.launch

class MataKuliahViewModel(private val repositoryMK: RepositoryMK) : ViewModel() {

    var uiState by mutableStateOf(MKUIState())

    fun updateState(matakuliahEvent: MataKuliahEvent) {
        uiState = uiState.copy(
            matakuliahEvent = matakuliahEvent,
        )
    }

    private fun validataFields(): Boolean {
        val event = uiState.matakuliahEvent
        val errorState = FormErrorState(
            kode = if (event.kode.isNotEmpty()) null else "KODE tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "Sks tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong",
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData() {
        val currentEvent = uiState.matakuliahEvent
        if (validataFields()) {
            viewModelScope.launch {
                try {
                    repositoryMK.insertMK(currentEvent.toMataKuliahEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        matakuliahEvent = MataKuliahEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else  {
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data anda"
            )
        }
    }
    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }
}

data class MKUIState(
    val matakuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
)

data class FormErrorState(
    val kode: String? = null,
    val nama: String? = null,
    val sks: String? = null,
    val jenis: String? = null,
    val dosenPengampu: String? = null
) {
    fun isValid(): Boolean {
        return kode == null && nama == null && sks == null &&
                jenis == null && dosenPengampu == null
    }
}

data class MataKuliahEvent (
    val kode: String = "",
    val nama: String = "",
    val sks: String = "",
    val jenis: String = "",
    val dosenPengampu: String = ""
)

fun MataKuliahEvent.toMataKuliahEntity(): MataKuliah = MataKuliah (
    kode = kode,
    nama = nama,
    sks = sks,
    jenis = jenis,
    dosenPengampu = dosenPengampu
)