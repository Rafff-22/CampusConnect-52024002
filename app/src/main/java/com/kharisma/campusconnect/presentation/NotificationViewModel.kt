package com.kharisma.campusconnect.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kharisma.campusconnect.data.NotificationSocketManager
import com.kharisma.campusconnect.data.SocketStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val socketManager: NotificationSocketManager
) : ViewModel() {
    private val _status = MutableStateFlow(SocketStatus.DISCONNECTED)
    val status: StateFlow<SocketStatus> = _status

    private val _messages = MutableStateFlow<List<String>>(emptyList())
    val messages: StateFlow<List<String>> = _messages

    private val _lastError = MutableStateFlow<String?>(null)
    val lastError: StateFlow<String?> = _lastError

    init {
        socketManager.status
            .onEach { _status.value = it }
            .launchIn(viewModelScope)

        socketManager.lastError
            .onEach { _lastError.value = it }
            .launchIn(viewModelScope)

        viewModelScope.launch {
            socketManager.messages.collect { pesan ->
                _messages.value = listOf(pesan) + _messages.value
            }
        }
    }

    fun connect() = socketManager.connect()

    fun sendMessage(pesan: String) = socketManager.sendMessage(pesan)

    fun disconnect() = socketManager.disconnect()

    override fun onCleared() {
        disconnect()
        super.onCleared()
    }
}
