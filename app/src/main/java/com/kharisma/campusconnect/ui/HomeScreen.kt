package com.kharisma.campusconnect.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kharisma.campusconnect.shared.MataKuliah

@Composable
fun HomeScreen(daftarMK: List<MataKuliah>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(daftarMK) { mk ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(mk.nama, style = MaterialTheme.typography.titleMedium)
                    Text("${mk.sks} SKS • ${mk.dosen}")
                }
            }
        }
    }
}