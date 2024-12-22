package com.example.ucp2_176_pam.ui.viewmodel.matakuliah

import com.example.ucp2_176_pam.data.entity.MataKuliah

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