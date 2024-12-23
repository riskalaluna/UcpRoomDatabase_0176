package com.example.ucp2_176_pam.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

//mendefinisikan entitas untuk tabel matakuliah, terdiri dari 6 atribut
@Entity(tableName = "matakuliah")
data class MataKuliah(
    @PrimaryKey
    val kode: String,
    val nama: String,
    val sks: String,
    val semester: String,
    val jenis: String,
    val dosenPengampu: String
)