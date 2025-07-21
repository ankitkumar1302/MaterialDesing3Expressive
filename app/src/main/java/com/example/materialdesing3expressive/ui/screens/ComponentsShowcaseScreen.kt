package com.example.materialdesing3expressive.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Animation
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FormatColorFill
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.materialdesing3expressive.ui.components.AnimatedFilterChipGroup
import com.example.materialdesing3expressive.ui.components.ButtonGroupShowcase
import com.example.materialdesing3expressive.ui.components.ColorAdjustableCard
import com.example.materialdesing3expressive.ui.components.ExpressiveExpandableCard
import com.example.materialdesing3expressive.ui.components.ExpressiveProgressIndicators
import com.example.materialdesing3expressive.ui.components.FloatingActionButtonMenuPreview
import com.example.materialdesing3expressive.ui.components.InteractiveCardContent
import com.example.materialdesing3expressive.ui.components.MyCustomLoadingIndicator
import com.example.materialdesing3expressive.ui.components.MyHorizontalFloatingToolbar
import com.example.materialdesing3expressive.ui.components.MyVerticalFloatingToolbar
import com.example.materialdesing3expressive.ui.components.UnifiedTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponentsShowcaseScreen(
    navController: NavController = rememberNavController()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            UnifiedTopAppBar(
                title = "Material 3 Components",
                navController = navController,
                scrollBehavior = scrollBehavior,
                isMainScreen = false
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Material Design 3 with Expressive UI",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            // Animated Filter Chip Groups
            ExpressiveExpandableCard(
                title = "Filter Chip Groups",
                subtitle = "Animated selection with haptic feedback",
                icon = {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                val categories = listOf(
                    "All", "Design", "Development",
                    "Business", "Marketing", "Music"
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("Single Selection")
                    AnimatedFilterChipGroup(
                        items = categories,
                        onSelectionChange = { }
                    )

                    Text("Multi Selection")
                    AnimatedFilterChipGroup(
                        items = categories,
                        multiSelection = true,
                        elevated = true,
                        onSelectionChange = { }
                    )
                }
            }

            // Button Group
            ExpressiveExpandableCard(
                title = "Button Groups",
                subtitle = "Fluid animations and interactions",
                icon = {
                    Icon(
                        imageVector = Icons.Default.Animation,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                ButtonGroupShowcase()
            }

            // Color Adjustable Card
            ExpressiveExpandableCard(
                title = "Interactive Card",
                subtitle = "Dynamic color and shape adaptation",
                icon = {
                    Icon(
                        imageVector = Icons.Default.FormatColorFill,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                initiallyExpanded = true
            ) {
                ColorAdjustableCard(
                    title = "Color Card"
                ) { bgColor ->
                    InteractiveCardContent(bgColor)
                }
            }

            // Progress Indicators
            ExpressiveExpandableCard(
                title = "Progress Indicators",
                subtitle = "Configurable animation and styling",
                icon = {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                ExpressiveProgressIndicators()
            }

            // Loading Indicator
            ExpressiveExpandableCard(
                title = "Loading Indicator",
                subtitle = "Material 3 loading animation",
                icon = {
                    Icon(
                        imageVector = Icons.Default.Speed,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                MyCustomLoadingIndicator()
            }

            // FAB Menu
            ExpressiveExpandableCard(
                title = "FAB Menu",
                subtitle = "Expandable floating action button menu",
                icon = {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                initiallyExpanded = false
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    FloatingActionButtonMenuPreview()
                }
            }

            // Horizontal Floating Toolbar
            ExpressiveExpandableCard(
                title = "Horizontal Floating Toolbar",
                subtitle = "Expandable horizontal action toolbar",
                icon = {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                initiallyExpanded = false
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                ) {
                    MyHorizontalFloatingToolbar()
                }
            }

            // Vertical Floating Toolbar
            ExpressiveExpandableCard(
                title = "Vertical Floating Toolbar",
                subtitle = "Expandable vertical action toolbar",
                icon = {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                },
                initiallyExpanded = false
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                ) {
                    MyVerticalFloatingToolbar()
                }
            }

            Divider(Modifier.padding(horizontal = 16.dp))

            Text(
                text = "Material Design 3 Expressive UI enhances standard Material components with fluid animations, dynamic interactions, and rich visual feedback.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun ComponentsShowcaseScreenPreview() {
    MaterialTheme {
        Surface {
            ComponentsShowcaseScreen()
        }
    }
}