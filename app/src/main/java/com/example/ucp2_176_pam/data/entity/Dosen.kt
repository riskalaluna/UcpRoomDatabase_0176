package com.example.ucp2_176_pam.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

//mendefinisikan entitas untuk tabel dosen, terdiri dari 3 atribut
@Entity(tableName = "dosen")
data class Dosen(
    @PrimaryKey
    val nidn: String,
    val nama: String,
    val jenisKelamin: String
)
