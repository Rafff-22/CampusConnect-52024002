package com.kharisma.campusconnect.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.mockwebserver.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NotificationSocketManagerTest {
    @Test
    fun connectKirimPesanDanPutuskan() = runBlocking {
        val server = MockWebServer()
        val echoListener = object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                webSocket.send(text)
            }
        }
        server.enqueue(MockResponse().withWebSocketUpgrade(echoListener))
        server.start()

        val url = "ws://${server.hostName}:${server.port}"
        val manager = NotificationSocketManager(url)
        val statusChannel = Channel<SocketStatus>(Channel.UNLIMITED)
        val pesanChannel = Channel<String>(Channel.UNLIMITED)
        val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
        val statusJob = manager.status.onEach { statusChannel.send(it) }.launchIn(scope)
        val pesanJob = manager.messages.onEach { pesanChannel.send(it) }.launchIn(scope)

        try {
            manager.connect()

            withTimeout(10_000) {
                while (statusChannel.receive() != SocketStatus.CONNECTED) {
                    // Menunggu status CONNECTED dari callback onOpen.
                }

                assertTrue(manager.sendMessage("halo dari tes"))
                assertEquals("halo dari tes", pesanChannel.receive())

                manager.disconnect()
                assertEquals(SocketStatus.DISCONNECTED, statusChannel.receive())
            }
        } finally {
            manager.disconnect()
            statusJob.cancel()
            pesanJob.cancel()
            scope.cancel()
            server.shutdown()
        }
    }
}
