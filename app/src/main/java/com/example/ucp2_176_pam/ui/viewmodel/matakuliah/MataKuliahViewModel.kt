package com.example.ucp2_176_pam.ui.viewmodel.matakuliah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2_176_pam.data.entity.Dosen
import com.example.ucp2_176_pam.data.entity.MataKuliah
import com.example.ucp2_176_pam.repository.RepositoryDsn
import com.example.ucp2_176_pam.repository.RepositoryMK
import kotlinx.coroutines.launch

//mengelola state yang berkaitan dengan data matkul
class MataKuliahViewModel(
    private val repositoryMK: RepositoryMK,
    private val repositoryDsn: RepositoryDsn
) : ViewModel() {

    // Menambahkan variabel UI State untuk MataKuliah
    var uiStateMataKuliah by mutableStateOf(MKUIState())
        private set

    //Menambahkan variable UI state untuk Dosen
    var dosenList by mutableStateOf<List<Dosen>>(emptyList())
        private set

    init {
        // Mengambil data dosen dari repository
        viewModelScope.launch {
            repositoryDsn.getAllDsn().collect { dosenList ->
                this@MataKuliahViewModel.dosenList = dosenList
                updateUiState()
            }
        }
    }

    // Update state berdasarkan event MataKuliah
    fun updateStateMataKuliah(matakuliahEvent: MataKuliahEvent) {
        uiStateMataKuliah = uiStateMataKuliah.copy(
            mataKuliahEvent = matakuliahEvent
        )
    }

    // Validasi input dari form
    fun validataFields(): Boolean {
        val event = uiStateMataKuliah.mataKuliahEvent
        val errorState = FormErrorState(
            kode = if (event.kode.isNotEmpty()) null else "KODE tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "Sks tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong"
        )

        // Update error state untuk validasi
        uiStateMataKuliah = uiStateMataKuliah.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // Simpan data mata kuliah
    fun saveData() {
        val currentEvent = uiStateMataKuliah.mataKuliahEvent
        if (validataFields()) {
            viewModelScope.launch {
                try {
                    // Menyimpan data mata kuliah ke repository
                    repositoryMK.insertMK(currentEvent.toMataKuliahEntity())
                    uiStateMataKuliah = uiStateMataKuliah.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        mataKuliahEvent = MataKuliahEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception) {
                    uiStateMataKuliah = uiStateMataKuliah.copy(
                        snackBarMessage = "Data berhasil disimpan"
                    )
                }
            }
        } else {
            uiStateMataKuliah = uiStateMataKuliah.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data anda"
            )
        }
    }

    // Reset pesan snackBar
    fun resetSnackBarMessage() {
        uiStateMataKuliah = uiStateMataKuliah.copy(snackBarMessage = null)
    }

    // Update UI State setelah dosen data berhasil didapatkan
    private fun updateUiState() {
        uiStateMataKuliah = uiStateMataKuliah.copy(dosenList = dosenList)
    }
}

// State UI untuk Mata Kuliah
data class MKUIState(
    val mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
    val dosenList: List<Dosen> = emptyList()
)

// State untuk error form
data class FormErrorState(
    val kode: String? = null,
    val nama: String? = null,
    val sks: String? = null,
    val semester: String? = null,
    val jenis: String? = null,
    val dosenPengampu: String? = null
) {
    // Validasi jika semua field valid
    fun isValid(): Boolean {
        return kode == null && nama == null && sks == null && semester == null &&
                jenis == null && dosenPengampu == null
    }
}

// Event yang merepresentasikan data Mata Kuliah
data class MataKuliahEvent(
    val kode: String = "",
    val nama: String = "",
    val sks: String = "",
    val semester: String = "",
    val jenis: String = "",
    val dosenPengampu: String = ""
)

// Mengubah MataKuliahEvent menjadi entity MataKuliah untuk disimpan ke database
fun MataKuliahEvent.toMataKuliahEntity(): MataKuliah = MataKuliah(
    kode = kode,
    nama = nama,
    sks = sks,
    semester = semester,
    jenis = jenis,
    dosenPengampu = dosenPengampu
)