package com.example.materialdesing3expressive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SplitButtonDefaults
import androidx.compose.material3.SplitButtonLayout
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.materialdesing3expressive.ui.components.AnimatedButtonGroup
import com.example.materialdesing3expressive.ui.components.AnimatedFilterChipGroup
import com.example.materialdesing3expressive.ui.components.ButtonGroupShowcase
import com.example.materialdesing3expressive.ui.components.ExpressiveExpandableCard
import com.example.materialdesing3expressive.ui.components.IconOnlyButtonGroup
import com.example.materialdesing3expressive.ui.components.MultiSelectButtonGroup
import com.example.materialdesing3expressive.ui.components.MyHorizontalFloatingToolbar
import com.example.materialdesing3expressive.ui.components.MyVerticalFloatingToolbar
import com.example.materialdesing3expressive.ui.components.SingleSelectConnectedButtonGroup
import com.example.materialdesing3expressive.ui.screens.ComponentsShowcaseScreen
import com.example.materialdesing3expressive.ui.theme.MaterialDesing3ExpressiveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialDesing3ExpressiveTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
//                    ElevatedSplitButton()
//                    FloatingActionButtonMenuPreview()
//                    MyCustomLoadingIndicator()
//                    SingleSelectConnectedButtonGroup()
//                    MultiSelectButtonGroup()
//                    AnimatedButtonGroup()
//                    IconOnlyButtonGroup()
//                    ButtonGroupShowcase()
//                    MyHorizontalFloatingToolbar()
//                    MyVerticalFloatingToolbar()

                    ComponentsShowcaseScreen()
//                    val categories = listOf(
//                        "All", "Design", "Development", "Business",
//                        "Marketing", "Photography", "Music", "Lifestyle"
//                    )
//
//                    var selectedSingleIndex by remember { mutableStateOf(listOf<Int>()) }
//                    var selectedMultiIndices by remember { mutableStateOf(listOf<Int>()) }
//
//                    AnimatedFilterChipGroup(
//                        items = categories,
//                        selectedIndices = selectedSingleIndex,
//                        onSelectionChange = { selectedSingleIndex = it }
//                    )
//
//                    Text(
//                        "Multi Selection Chips",
//                        style = MaterialTheme.typography.titleMedium
//                    )
//                    AnimatedFilterChipGroup(
//                        items = categories,
//                        selectedIndices = selectedMultiIndices,
//                        multiSelection = true,
//                        onSelectionChange = { selectedMultiIndices = it }
//                    )
//
//                    Text(
//                        "Elevated Multi Selection Chips",
//                        style = MaterialTheme.typography.titleMedium
//                    )
//                    AnimatedFilterChipGroup(
//                        items = categories,
//                        selectedIndices = selectedMultiIndices,
//                        multiSelection = true,
//                        elevated = true,
//                        onSelectionChange = { selectedMultiIndices = it }
//                    )




//                    ExpressiveExpandableCard(
//                        title = "Another Card",
//                        subtitle = "With different content",
//                        initiallyExpanded = false
//                    ) {
//                        Column {
//                            Text("This card showcases multi-paragraph content that gets revealed when expanded.")
//                            Spacer(modifier = Modifier.height(8.dp))
//                            Text("You can include any composable content in the expanded section.")
//                        }
//                    }



                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
@Preview(showBackground = true)
fun ElevatedSplitButton() {
    var checked by remember { mutableStateOf(false) }

    SplitButtonLayout(
        leadingButton = {
            SplitButtonDefaults.ElevatedLeadingButton(
                onClick = { /* TODO: Add primary action */ },
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit, // Example Icon
                    contentDescription = "Edit Action", // TODO: Meaningful description
                    modifier = Modifier.size(SplitButtonDefaults.LeadingIconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    text = "My Action", // TODO: Meaningful text
                    style = MaterialTheme.typography.labelLarge // Adjusted for typical button text
                )
            }
        },
        trailingButton = {
            SplitButtonDefaults.ElevatedTrailingButton(
                checked = checked,
                onCheckedChange = { checked = it },
            ) {
                val icon = if (checked) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
                Icon(
                    imageVector = icon,
                    contentDescription = if (checked) "Hide options" else "Show options", // TODO: Meaningful descriptions
                    modifier = Modifier.size(SplitButtonDefaults.TrailingIconSize)
                )
            }
        })
}


