package com.kharisma.campusconnect.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("CampusConnect", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onNavigate("courses") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Mata Kuliah")
        }

        Button(
            onClick = { onNavigate("announcement") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pengumuman Kampus")
        }

        Button(
            onClick = { onNavigate("info_mitra") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Info Mitra")
        }

        Button(
            onClick = { onNavigate("notification") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Notifikasi Live")
        }
    }
}
