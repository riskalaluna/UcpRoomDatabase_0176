package com.example.ucp2_176_pam.ui.viewmodel.matakuliah

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2_176_pam.data.entity.MataKuliah
import com.example.ucp2_176_pam.repository.RepositoryMK
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

///bertanggung jawab untuk mengelola data matkul dan memberikan state yang relevan untuk tampilan
class HomeMataKuliahViewModel (
    private val repositoryMK: RepositoryMK
) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> = repositoryMK.getAllMK()
        .filterNotNull()
        .map {
            HomeUiState(
                listMK = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeUiState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState(
                isLoading = true,
            )
        )
}

////berisi data dan status UI
data class HomeUiState(
    val listMK: List<MataKuliah> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)