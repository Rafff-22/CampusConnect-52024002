package com.kharisma.campusconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kharisma.campusconnect.shared.CourseRepositoryImpl
import com.kharisma.campusconnect.ui.theme.CampusConnectTheme
import com.kharisma.campusconnect.ui.HomeScreen

class MainActivity : ComponentActivity() {
    private val repository = CourseRepositoryImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampusConnectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        daftarMK = repository.getAllCourses(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
