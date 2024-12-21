package com.example.ucp2_176_pam.ui.viewmodel.dosen

import com.example.ucp2_176_pam.data.entity.Dosen

data class HomeUiState(
    val listDsn: List<Dosen> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)