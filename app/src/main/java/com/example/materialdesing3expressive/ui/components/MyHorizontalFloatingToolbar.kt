package com.example.materialdesing3expressive.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MyHorizontalFloatingToolbar() {
    var expanded by rememberSaveable { mutableStateOf(true) }
    // Example using vibrant colors, you can use standard or create custom
    val vibrantColors = FloatingToolbarDefaults.vibrantFloatingToolbarColors()

    Scaffold { innerPadding -> // Correctly capture innerPadding from Scaffold
        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding) // Apply innerPadding to the Box
        ) {
            // Example scrollable content
            Column(
                Modifier.verticalScroll(rememberScrollState())
            ) {
                Text(text = rememberSaveable { LoremIpsum(words = 800).values.first() })
            }

            HorizontalFloatingToolbar(
                expanded = expanded,
                floatingActionButton = { // Corrected: This should be a composable lambda
                    FloatingToolbarDefaults.VibrantFloatingActionButton(
                        onClick = { /* TODO: Handle FAB click */ },
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Add Action")
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                colors = vibrantColors,
                content = { // Corrected: This should be a composable lambda
                    IconButton(onClick = { /* TODO: Handle person action */ }) {
                        Icon(Icons.Filled.Person, contentDescription = "Person")
                    }
                    IconButton(onClick = { /* TODO: Handle edit action */ }) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit")
                    }
                    // Add more IconButton or other actions as needed
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyHorizontalFloatingToolbarPreview() {
    MyHorizontalFloatingToolbar()
}
