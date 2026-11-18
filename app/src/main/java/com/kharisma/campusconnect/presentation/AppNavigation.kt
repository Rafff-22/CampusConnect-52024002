package com.kharisma.campusconnect.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "menu",
        modifier = modifier
    ) {
        composable("menu") {
            MenuScreen(
                modifier = Modifier,
                onNavigate = { rute -> navController.navigate(rute) }
            )
        }
        composable("courses") {
            HomeScreen(modifier = Modifier)
        }
        composable("announcement") {
            AnnouncementScreen(modifier = Modifier)
        }
        composable("info_mitra") {
            InfoMitraScreen(modifier = Modifier)
        }
        composable("notification") {
            NotificationScreen(modifier = Modifier)
        }
    }
}
