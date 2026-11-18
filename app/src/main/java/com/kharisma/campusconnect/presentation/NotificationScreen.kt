package com.kharisma.campusconnect.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kharisma.campusconnect.data.SocketStatus

@Composable
fun NotificationScreen(
    modifier: Modifier = Modifier,
    viewModel: NotificationViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsState()
    val daftarPesan by viewModel.messages.collectAsState()
    val lastError by viewModel.lastError.collectAsState()
    var pesan by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text("Notifikasi Live", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Status koneksi: $status")
        if (status == SocketStatus.ERROR && lastError != null) {
            Text(
                lastError.orEmpty(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { viewModel.connect() },
                enabled = status != SocketStatus.CONNECTED
            ) {
                Text("Hubungkan")
            }
            Button(onClick = { viewModel.disconnect() }) {
                Text("Putuskan")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = pesan,
            onValueChange = { pesan = it },
            label = { Text("Pesan") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                viewModel.sendMessage(pesan)
                pesan = ""
            },
            enabled = status == SocketStatus.CONNECTED && pesan.isNotBlank()
        ) {
            Text("Kirim Pesan")
        }

        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(daftarPesan) { isiPesan ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        isiPesan,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
    }
}
