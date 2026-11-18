package com.kharisma.campusconnect.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kharisma.campusconnect.domain.GetCountriesUseCase
import com.kharisma.campusconnect.domain.NegaraMitra
import com.kharisma.campusconnect.domain.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface InfoMitraUiState {
    data object Loading : InfoMitraUiState
    data class Success(val daftar: List<NegaraMitra>) : InfoMitraUiState
    data class Error(val pesan: String) : InfoMitraUiState
}

@HiltViewModel
class InfoMitraViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<InfoMitraUiState>(InfoMitraUiState.Loading)
    val uiState: StateFlow<InfoMitraUiState> = _uiState

    init {
        muatData()
    }

    fun muatData() {
        viewModelScope.launch {
            _uiState.value = InfoMitraUiState.Loading
            when (val hasil = getCountriesUseCase()) {
                is Result.Success -> _uiState.value = InfoMitraUiState.Success(hasil.data)
                is Result.Error -> _uiState.value = InfoMitraUiState.Error(hasil.message)
            }
        }
    }

    fun muatUlang() = muatData()
}
