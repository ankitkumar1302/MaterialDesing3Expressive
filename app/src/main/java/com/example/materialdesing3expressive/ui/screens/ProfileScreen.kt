package com.example.materialdesing3expressive.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.materialdesing3expressive.ui.components.UnifiedTopAppBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope

data class StatItem(
    val label: String,
    val value: String,
    val icon: ImageVector,
    val progress: Float = 0f
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    
    // Calculate scroll progress for color transition
    val scrollProgress = remember {
        derivedStateOf {
            val scrollValue = scrollState.value.toFloat()
            val maxScroll = 200f // Transition completes after 200px scroll
            (scrollValue / maxScroll).coerceIn(0f, 1f)
        }
    }
    
    // Animate top bar color based on scroll
    val topBarColor by animateColorAsState(
        targetValue = if (scrollProgress.value > 0.5f) 
            MaterialTheme.colorScheme.surface 
        else 
            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
        animationSpec = tween(300),
        label = "topBarColor"
    )
    
    val contentColor by animateColorAsState(
        targetValue = if (scrollProgress.value > 0.5f) 
            MaterialTheme.colorScheme.onSurface 
        else 
            MaterialTheme.colorScheme.primary,
        animationSpec = tween(300),
        label = "contentColor"
    )
    
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Add top padding for transparent app bar
            Spacer(modifier = Modifier.height(64.dp))
            
            // Profile Header
            ProfileHeader(coroutineScope)
            
            // Stats Section
            StatsSection()
            
            // Achievements Section
            AchievementsSection()
            
            // Settings Options
            ProfileOptions(navController)
        }
        
        // Custom Top App Bar with scroll-based color
        TopAppBar(
            title = { 
                Text(
                    "Profile",
                    color = contentColor
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        Icons.Default.ArrowBack, 
                        contentDescription = "Back",
                        tint = contentColor
                    )
                }
            },
            actions = {
                IconButton(onClick = { /* Edit profile */ }) {
                    Icon(
                        Icons.Default.Edit, 
                        contentDescription = "Edit Profile",
                        tint = contentColor
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = topBarColor
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ProfileHeader(coroutineScope: CoroutineScope) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
    ) {
        // Background Gradient
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                )
        )
        
        // Profile Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            
            // Profile Picture with Animation using Coroutines
            var imageScale by remember { mutableFloatStateOf(0.8f) }
            val scale by animateFloatAsState(
                targetValue = imageScale,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                ),
                label = "profileImageScale"
            )
            
            LaunchedEffect(Unit) {
                coroutineScope.launch {
                    delay(300) // Initial delay
                    imageScale = 1.1f
                    delay(200)
                    imageScale = 1f
                }
            }
            
            Surface(
                modifier = Modifier
                    .size(120.dp)
                    .scale(scale),
                shape = CircleShape,
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(60.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // User Info
            Text(
                text = "John Doe",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "john.doe@example.com",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // User Badge
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Premium Member",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
}

@Composable
fun StatsSection() {
    val coroutineScope = rememberCoroutineScope()
    val stats = listOf(
        StatItem("Projects", "12", Icons.Default.Folder, 0.8f),
        StatItem("Components", "48", Icons.Default.Dashboard, 0.6f),
        StatItem("Contributions", "156", Icons.Default.Code, 0.9f),
        StatItem("Achievements", "8", Icons.Default.EmojiEvents, 0.4f)
    )
    
    Column(
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Statistics",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        stats.chunked(2).forEachIndexed { index, rowStats ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowStats.forEach { stat ->
                    StatCard(
                        stat = stat,
                        modifier = Modifier.weight(1f),
                        animationDelay = index * 150L,
                        coroutineScope = coroutineScope
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun StatCard(
    stat: StatItem,
    modifier: Modifier = Modifier,
    animationDelay: Long = 0L,
    coroutineScope: CoroutineScope
) {
    var isVisible by remember { mutableStateOf(false) }
    var animatedProgress by remember { mutableFloatStateOf(0f) }
    
    // Animate card appearance and progress
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            delay(animationDelay)
            isVisible = true
            
            // Animate progress bar after card appears
            delay(300)
            launch {
                var progress = 0f
                while (progress < stat.progress) {
                    progress += 0.02f
                    animatedProgress = progress.coerceAtMost(stat.progress)
                    delay(10)
                }
            }
        }
    }
    
    val scale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "cardScale"
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(600),
        label = "cardAlpha"
    )
    
    Card(
        modifier = modifier
            .scale(scale)
            .graphicsLayer { this.alpha = alpha },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = stat.icon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stat.label,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = stat.value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            if (stat.progress > 0f) {
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
    }
}

@Composable
fun AchievementsSection() {
    val achievements = listOf(
        Triple(Icons.Default.Star, "Early Adopter", MaterialTheme.colorScheme.primary),
        Triple(Icons.Default.Rocket, "Fast Learner", MaterialTheme.colorScheme.secondary),
        Triple(Icons.Default.Favorite, "Community Love", MaterialTheme.colorScheme.tertiary),
        Triple(Icons.Default.TrendingUp, "Rising Star", MaterialTheme.colorScheme.error)
    )
    
    Column(
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Recent Achievements",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            achievements.forEach { (icon, title, color) ->
                AchievementBadge(
                    icon = icon,
                    title = title,
                    color = color
                )
            }
        }
    }
}

@Composable
fun AchievementBadge(
    icon: ImageVector,
    title: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.size(56.dp),
            shape = CircleShape,
            color = color.copy(alpha = 0.1f)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    modifier = Modifier.size(28.dp),
                    tint = color
                )
            }
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileOptions(navController: NavController) {
    val options = listOf(
        Triple(Icons.Default.Settings, "Settings") { navController.navigate("settings") },
        Triple(Icons.Default.Security, "Privacy") { },
        Triple(Icons.Default.Help, "Help & Support") { },
        Triple(Icons.Default.Info, "About") { navController.navigate("about") },
        Triple(Icons.Default.Logout, "Sign Out") { }
    )
    
    Column(
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Options",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column {
                options.forEachIndexed { index, (icon, title, onClick) ->
                    ListItem(
                        headlineContent = { 
                            Text(
                                text = title,
                                color = if (title == "Sign Out") 
                                    MaterialTheme.colorScheme.error 
                                else 
                                    MaterialTheme.colorScheme.onSurface
                            )
                        },
                        leadingContent = {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = if (title == "Sign Out") 
                                    MaterialTheme.colorScheme.error 
                                else 
                                    MaterialTheme.colorScheme.onSurfaceVariant
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
                    
                    if (index < options.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}