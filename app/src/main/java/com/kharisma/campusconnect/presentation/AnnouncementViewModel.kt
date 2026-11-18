package com.kharisma.campusconnect.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kharisma.campusconnect.domain.GetAnnouncementsUseCase
import com.kharisma.campusconnect.domain.Result
import com.kharisma.campusconnect.shared.Pengumuman
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface AnnouncementUiState {
    data object Loading : AnnouncementUiState
    data class Success(val daftar: List<Pengumuman>) : AnnouncementUiState
    data class Error(val pesan: String) : AnnouncementUiState
}

@HiltViewModel
class AnnouncementViewModel @Inject constructor(
    private val getAnnouncementsUseCase: GetAnnouncementsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<AnnouncementUiState>(AnnouncementUiState.Loading)
    val uiState: StateFlow<AnnouncementUiState> = _uiState

    init {
        muatUlang()
    }

    fun muatUlang() {
        viewModelScope.launch {
            _uiState.value = AnnouncementUiState.Loading
            when (val hasil = getAnnouncementsUseCase()) {
                is Result.Success -> _uiState.value = AnnouncementUiState.Success(hasil.data)
                is Result.Error -> _uiState.value = AnnouncementUiState.Error(hasil.message)
            }
        }
    }
}
