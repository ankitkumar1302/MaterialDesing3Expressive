package com.example.materialdesing3expressive.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import com.example.materialdesing3expressive.ui.components.UnifiedTopAppBar

data class SettingItem(
    val title: String,
    val subtitle: String? = null,
    val icon: ImageVector,
    val type: SettingType = SettingType.Navigation
)

enum class SettingType {
    Toggle, Navigation, Action
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    var darkThemeEnabled by remember { mutableStateOf(false) }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var soundEnabled by remember { mutableStateOf(true) }
    var vibrationEnabled by remember { mutableStateOf(false) }
    var dynamicColorEnabled by remember { mutableStateOf(true) }
    
    Scaffold(
        topBar = {
            UnifiedTopAppBar(
                title = "Settings",
                navController = navController,
                isMainScreen = true
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Appearance Section
            SettingsSection(title = "Appearance") {
                ToggleSettingItem(
                    icon = Icons.Default.DarkMode,
                    title = "Dark Theme",
                    subtitle = "Use dark theme across the app",
                    checked = darkThemeEnabled,
                    onCheckedChange = { darkThemeEnabled = it }
                )
                
                ToggleSettingItem(
                    icon = Icons.Default.Palette,
                    title = "Dynamic Colors",
                    subtitle = "Use colors from your wallpaper",
                    checked = dynamicColorEnabled,
                    onCheckedChange = { dynamicColorEnabled = it }
                )
                
                NavigationSettingItem(
                    icon = Icons.Default.FormatSize,
                    title = "Font Size",
                    subtitle = "Medium",
                    onClick = { }
                )
            }
            
            // Notifications Section
            SettingsSection(title = "Notifications") {
                ToggleSettingItem(
                    icon = Icons.Default.Notifications,
                    title = "Push Notifications",
                    subtitle = "Receive app notifications",
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it }
                )
                
                ToggleSettingItem(
                    icon = Icons.Default.VolumeUp,
                    title = "Sound",
                    subtitle = "Play sound for notifications",
                    checked = soundEnabled,
                    onCheckedChange = { soundEnabled = it },
                    enabled = notificationsEnabled
                )
                
                ToggleSettingItem(
                    icon = Icons.Default.Vibration,
                    title = "Vibration",
                    subtitle = "Vibrate for notifications",
                    checked = vibrationEnabled,
                    onCheckedChange = { vibrationEnabled = it },
                    enabled = notificationsEnabled
                )
            }
            
            // Privacy Section
            SettingsSection(title = "Privacy & Security") {
                NavigationSettingItem(
                    icon = Icons.Default.Lock,
                    title = "Privacy Policy",
                    onClick = { }
                )
                
                NavigationSettingItem(
                    icon = Icons.Default.Security,
                    title = "Security Settings",
                    subtitle = "Manage your security preferences",
                    onClick = { }
                )
                
                NavigationSettingItem(
                    icon = Icons.Default.PersonRemove,
                    title = "Blocked Users",
                    onClick = { }
                )
            }
            
            // Data & Storage Section
            SettingsSection(title = "Data & Storage") {
                NavigationSettingItem(
                    icon = Icons.Default.CloudDownload,
                    title = "Data Usage",
                    subtitle = "1.2 GB this month",
                    onClick = { }
                )
                
                NavigationSettingItem(
                    icon = Icons.Default.Storage,
                    title = "Storage",
                    subtitle = "Manage app storage",
                    onClick = { }
                )
                
                ActionSettingItem(
                    icon = Icons.Default.DeleteForever,
                    title = "Clear Cache",
                    subtitle = "Free up space",
                    onClick = { }
                )
            }
            
            // About Section
            SettingsSection(title = "About") {
                NavigationSettingItem(
                    icon = Icons.Default.Info,
                    title = "About App",
                    subtitle = "Version 1.0.0",
                    onClick = { navController.navigate("about") }
                )
                
                NavigationSettingItem(
                    icon = Icons.Default.Code,
                    title = "Open Source Licenses",
                    onClick = { }
                )
                
                NavigationSettingItem(
                    icon = Icons.Default.Feedback,
                    title = "Send Feedback",
                    onClick = { }
                )
            }
            
            // Account Section
            SettingsSection(title = "Account") {
                ActionSettingItem(
                    icon = Icons.Default.Logout,
                    title = "Sign Out",
                    subtitle = "Sign out from your account",
                    onClick = { },
                    isDestructive = true
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.animateContentSize()
            ) {
                content()
            }
        }
    }
}

@Composable
fun ToggleSettingItem(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true
) {
    ListItem(
        headlineContent = { 
            Text(
                text = title,
                color = if (enabled) 
                    MaterialTheme.colorScheme.onSurface 
                else 
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        },
        supportingContent = subtitle?.let { 
            { 
                Text(
                    text = it,
                    color = if (enabled) 
                        MaterialTheme.colorScheme.onSurfaceVariant 
                    else 
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            }
        },
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (enabled) 
                    MaterialTheme.colorScheme.onSurfaceVariant 
                else 
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )
        },
        trailingContent = {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = enabled
            )
        }
    )
}

@Composable
fun NavigationSettingItem(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = { Text(title) },
        supportingContent = subtitle?.let { { Text(it) } },
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        trailingContent = {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        modifier = Modifier.clickable { onClick() }
    )
}

@Composable
fun ActionSettingItem(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit,
    isDestructive: Boolean = false
) {
    val contentColor = if (isDestructive) 
        MaterialTheme.colorScheme.error 
    else 
        MaterialTheme.colorScheme.onSurface
    
    ListItem(
        headlineContent = { 
            Text(
                text = title,
                color = contentColor
            )
        },
        supportingContent = subtitle?.let { 
            { 
                Text(
                    text = it,
                    color = if (isDestructive)
                        MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor
            )
        },
        modifier = Modifier.clickable { onClick() }
    )
}