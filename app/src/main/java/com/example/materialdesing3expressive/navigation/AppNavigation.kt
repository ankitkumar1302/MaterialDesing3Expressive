package com.example.materialdesing3expressive.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.materialdesing3expressive.ui.components.AnimatedNavigationBar
import com.example.materialdesing3expressive.ui.screens.AboutScreen
import com.example.materialdesing3expressive.ui.screens.ComponentsShowcaseScreen
import com.example.materialdesing3expressive.ui.screens.HomeScreen
import com.example.materialdesing3expressive.ui.screens.ProfileScreen
import com.example.materialdesing3expressive.ui.screens.SettingsScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ComponentsGallery : Screen("components_gallery")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
    object About : Screen("about")
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.ComponentsGallery.route,
        Screen.Profile.route,
        Screen.Settings.route
    )
    
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                AnimatedNavigationBar(navController = navController)
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0) // Handle insets manually
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navController = navController)
            }
            
            composable(Screen.ComponentsGallery.route) {
                ComponentsShowcaseScreen(navController = navController)
            }
            
            composable(Screen.Profile.route) {
                ProfileScreen(navController = navController)
            }
            
            composable(Screen.Settings.route) {
                SettingsScreen(navController = navController)
            }
            
            composable(Screen.About.route) {
                AboutScreen(navController = navController)
            }
        }
    }
}