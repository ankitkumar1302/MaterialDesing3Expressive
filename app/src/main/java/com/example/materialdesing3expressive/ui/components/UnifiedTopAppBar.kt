package com.example.materialdesing3expressive.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

/**
 * Unified Top App Bar component following Material 3 best practices
 * Provides consistent navigation and styling across all screens
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnifiedTopAppBar(
    title: String,
    navController: NavController? = null,
    navigationIcon: ImageVector? = Icons.AutoMirrored.Filled.ArrowBack,
    onNavigationClick: (() -> Unit)? = { navController?.navigateUp() },
    actions: @Composable () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    isMainScreen: Boolean = false
) {
    if (isMainScreen) {
        // Use CenterAlignedTopAppBar for main screens
        CenterAlignedTopAppBar(
            title = { 
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            navigationIcon = {
                if (navigationIcon != null && onNavigationClick != null && navController != null && 
                    navController.previousBackStackEntry != null) {
                    IconButton(onClick = onNavigationClick) {
                        Icon(
                            imageVector = navigationIcon,
                            contentDescription = "Navigate back"
                        )
                    }
                }
            },
            actions = { actions() },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                scrolledContainerColor = MaterialTheme.colorScheme.surface
            ),
            scrollBehavior = scrollBehavior
        )
    } else {
        // Use regular TopAppBar for secondary screens
        TopAppBar(
            title = { 
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
            },
            navigationIcon = {
                if (navigationIcon != null && onNavigationClick != null) {
                    IconButton(onClick = onNavigationClick) {
                        Icon(
                            imageVector = navigationIcon,
                            contentDescription = "Navigate back"
                        )
                    }
                }
            },
            actions = { actions() },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                scrolledContainerColor = MaterialTheme.colorScheme.surface
            ),
            scrollBehavior = scrollBehavior
        )
    }
}