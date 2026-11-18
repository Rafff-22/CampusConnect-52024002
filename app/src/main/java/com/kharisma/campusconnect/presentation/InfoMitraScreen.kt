package com.kharisma.campusconnect.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun InfoMitraScreen(
    modifier: Modifier = Modifier,
    viewModel: InfoMitraViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        InfoMitraUiState.Loading -> Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(8.dp))
            Text("Memuat informasi negara...")
        }

        is InfoMitraUiState.Success -> LazyColumn(modifier = modifier.fillMaxSize()) {
            items(state.daftar, key = { it.kode }) { negara ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            negara.nama,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Kode: ${negara.kode}")
                        Text("Ibu Kota: ${negara.ibuKota}")
                    }
                }
            }
        }

        is InfoMitraUiState.Error -> Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(state.pesan)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { viewModel.muatData() }) {
                Text("Coba Lagi")
            }
        }
    }
}
