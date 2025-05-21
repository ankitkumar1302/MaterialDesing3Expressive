package com.example.materialdesing3expressive.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Download // Example icon
import androidx.compose.material.icons.filled.MoreVert // Example icon
import androidx.compose.material.icons.filled.Person // Example icon
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalFloatingToolbar
import androidx.compose.material3.FloatingToolbarDefaults // For ScreenOffset
import androidx.compose.material3.AppBarColumn // For overflow in VerticalFloatingToolbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MyVerticalFloatingToolbar() {
    var expanded by rememberSaveable { mutableStateOf(true) }
    // You can also use FloatingToolbarDefaults.standardFloatingToolbarColors()
    val vibrantColors = FloatingToolbarDefaults.vibrantFloatingToolbarColors()

    Scaffold { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                state = rememberLazyListState(),
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(25) { Text(text = "Item $it") }
            }

            VerticalFloatingToolbar(
                modifier = Modifier
                    .align(Alignment.BottomEnd) // Example alignment
                    .offset(x = -FloatingToolbarDefaults.ScreenOffset), // Example offset
                expanded = expanded,
                colors = vibrantColors,
                leadingContent = { // Optional: content at the start (top for vertical)
                    // Example: Add some IconButtons if needed
                },
                trailingContent = { // Optional: content at the end (bottom for vertical), often an overflow
                    AppBarColumn(
                        overflowIndicator = { menuState ->
                            IconButton(
                                onClick = {
                                    if (menuState.isExpanded) menuState.dismiss() else menuState.show()
                                }
                            ) {
                                Icon(Icons.Filled.MoreVert, contentDescription = "More options")
                            }
                        }
                    ) {
                        clickableItem(
                            onClick = { /* TODO: Handle download */ expanded = false },
                            icon = { Icon(Icons.Filled.Download, contentDescription = "Download") },
                            label = "Download"
                        )
                        clickableItem(
                            onClick = { /* TODO: Handle person */ expanded = false },
                            icon = { Icon(Icons.Filled.Person, contentDescription = "Person") },
                            label = "Person"
                        )
                    }
                },
                content = { // Main FAB-like content
                    FilledIconButton(
                        modifier = Modifier.height(64.dp), // Example size
                        onClick = { expanded = !expanded /* Toggle or primary action */ }
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Toggle Toolbar")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyVerticalFloatingToolbarPreview() {
    MyVerticalFloatingToolbar()
}
