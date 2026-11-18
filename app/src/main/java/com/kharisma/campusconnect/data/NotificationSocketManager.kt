package com.kharisma.campusconnect.data

import android.util.Log
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class NotificationSocketManager(
    private val serverUrl: String = DEFAULT_WEBSOCKET_URL
) {
    private val _messages = MutableSharedFlow<String>(
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val messages: SharedFlow<String> = _messages

    private val _status = MutableSharedFlow<SocketStatus>(
        replay = 1,
        extraBufferCapacity = 8,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val status: SharedFlow<SocketStatus> = _status

    private val _lastError = MutableStateFlow<String?>(null)
    val lastError: StateFlow<String?> = _lastError

    private var webSocket: WebSocket? = null
    private var client: OkHttpClient? = null
    private var closedByClient = false

    fun connect() {
        closedByClient = false
        _lastError.value = null
        _status.tryEmit(SocketStatus.CONNECTING)

        val httpClient = OkHttpClient.Builder()
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .build()
        client = httpClient

        val request = Request.Builder()
            .url(serverUrl)
            .build()

        webSocket = httpClient.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.i("WS", "onOpen: HTTP ${response.code}")
                _lastError.value = null
                _status.tryEmit(SocketStatus.CONNECTED)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                _messages.tryEmit(text)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                webSocket.close(1000, null)
                _status.tryEmit(SocketStatus.DISCONNECTED)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Log.i("WS", "onClosed: code=$code reason=$reason")
                _status.tryEmit(SocketStatus.DISCONNECTED)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                val kodeHttp = response?.code?.toString() ?: "tidak ada"
                val detail = "${t.javaClass.simpleName}: ${t.message ?: "tanpa pesan"} (HTTP $kodeHttp)"
                Log.e("WS", "onFailure: $detail", t)
                if (!closedByClient) {
                    _lastError.value = detail
                    _status.tryEmit(SocketStatus.ERROR)
                }
            }
        })
    }

    fun sendMessage(pesan: String): Boolean = webSocket?.send(pesan) ?: false

    fun disconnect() {
        closedByClient = true
        _status.tryEmit(SocketStatus.DISCONNECTED)
        webSocket?.cancel()
        webSocket = null
        client?.dispatcher?.executorService?.shutdown()
        client = null
    }

    companion object {
        // wss://echo.websocket.events adalah server contoh dari modul.
        const val DEFAULT_WEBSOCKET_URL = "wss://ws.postman-echo.com/raw"
    }
}
