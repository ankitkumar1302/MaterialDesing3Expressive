package com.example.materialdesing3expressive.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * An animated filter chip group that supports single or multiple selection with fluid animations
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnimatedFilterChipGroup(
    items: List<String>,
    modifier: Modifier = Modifier,
    selectedIndices: List<Int> = emptyList(),
    multiSelection: Boolean = false,
    elevated: Boolean = false,
    allowDeselection: Boolean = true,
    chipColors: @Composable (selected: Boolean) -> Pair<Color, Color> = { selected ->
        if (selected) {
            Pair(
                MaterialTheme.colorScheme.primaryContainer,
                MaterialTheme.colorScheme.onPrimaryContainer
            )
        } else {
            Pair(
                MaterialTheme.colorScheme.surfaceVariant,
                MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    },
    onSelectionChange: (List<Int>) -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val selectedItems = remember { mutableStateListOf<Int>().apply { addAll(selectedIndices) } }

    // Use FlowRow for responsive wrapping of chips
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        maxItemsInEachRow = 6 // This is a rough estimate, will adapt based on screen size
    ) {
        items.forEachIndexed { index, item ->
            val selected = selectedItems.contains(index)
            val (containerColor, contentColor) = chipColors(selected)

            val animatedContainerColor by animateColorAsState(
                targetValue = containerColor,
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
                label = "containerColor"
            )

            val animatedContentColor by animateColorAsState(
                targetValue = contentColor,
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
                label = "contentColor"
            )

            val scale by animateFloatAsState(
                targetValue = if (selected) 1.05f else 1f,
                animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing),
                label = "scale"
            )

            if (elevated) {
                ElevatedFilterChip(
                    selected = selected,
                    onClick = {
                        handleChipSelection(
                            index,
                            selectedItems,
                            multiSelection,
                            allowDeselection,
                            onSelectionChange,
                            haptic
                        )
                    },
                    label = { Text(item) },
                    trailingIcon = {
                        if (selected) {
                            Box(
                                modifier = Modifier
                                    .size(18.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                        }
                    },
                    modifier = Modifier.scale(scale)
                )
            } else {
                FilterChip(
                    selected = selected,
                    onClick = {
                        handleChipSelection(
                            index,
                            selectedItems,
                            multiSelection,
                            allowDeselection,
                            onSelectionChange,
                            haptic
                        )
                    },
                    label = { Text(item) },
                    trailingIcon = {
                        if (selected) {
                            Box(
                                modifier = Modifier
                                    .size(18.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                        }
                    },
                    modifier = Modifier.scale(scale),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = animatedContainerColor,
                        selectedLabelColor = animatedContentColor
                    ),
                    border = if (!selected)
                        BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                    else null
                )
            }
        }
    }
}

private fun handleChipSelection(
    index: Int,
    selectedItems: MutableList<Int>,
    multiSelection: Boolean,
    allowDeselection: Boolean,
    onSelectionChange: (List<Int>) -> Unit,
    haptic: androidx.compose.ui.hapticfeedback.HapticFeedback
) {
    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)

    if (multiSelection) {
        if (selectedItems.contains(index)) {
            if (allowDeselection || selectedItems.size > 1) {
                selectedItems.remove(index)
            }
        } else {
            selectedItems.add(index)
        }
    } else {
        if (selectedItems.contains(index)) {
            if (allowDeselection) {
                selectedItems.clear()
            }
        } else {
            selectedItems.clear()
            selectedItems.add(index)
        }
    }

    onSelectionChange(selectedItems.toList())
}

@Preview(showBackground = true)
@Composable
fun AnimatedFilterChipGroupPreview() {
    val categories = listOf(
        "All", "Design", "Development", "Business",
        "Marketing", "Photography", "Music", "Lifestyle"
    )

    var selectedSingleIndex by remember { mutableStateOf(listOf<Int>()) }
    var selectedMultiIndices by remember { mutableStateOf(listOf<Int>()) }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                "Single Selection Chips",
                style = MaterialTheme.typography.titleMedium
            )
            AnimatedFilterChipGroup(
                items = categories,
                selectedIndices = selectedSingleIndex,
                onSelectionChange = { selectedSingleIndex = it }
            )

            Text(
                "Multi Selection Chips",
                style = MaterialTheme.typography.titleMedium
            )
            AnimatedFilterChipGroup(
                items = categories,
                selectedIndices = selectedMultiIndices,
                multiSelection = true,
                onSelectionChange = { selectedMultiIndices = it }
            )

            Text(
                "Elevated Multi Selection Chips",
                style = MaterialTheme.typography.titleMedium
            )
            AnimatedFilterChipGroup(
                items = categories,
                selectedIndices = selectedMultiIndices,
                multiSelection = true,
                elevated = true,
                onSelectionChange = { selectedMultiIndices = it }
            )
        }
    }
}