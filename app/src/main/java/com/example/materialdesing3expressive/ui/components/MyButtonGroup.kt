package com.example.materialdesing3expressive.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Work
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Preview(showBackground = true)
@Composable
fun ButtonGroupShowcase() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Button Groups",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Single Select with Icons and Text",
            style = MaterialTheme.typography.titleMedium
        )
        SingleSelectConnectedButtonGroup()

        Text(
            text = "Icon-Only Button Group",
            style = MaterialTheme.typography.titleMedium
        )
        IconOnlyButtonGroup()

        Text(
            text = "Multi-Select Button Group",
            style = MaterialTheme.typography.titleMedium
        )
        MultiSelectButtonGroup()

        Spacer(modifier = Modifier.size(16.dp))
        
        Text(
            text = "Animated Button Group",
            style = MaterialTheme.typography.titleMedium
        )
        AnimatedButtonGroup()
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SingleSelectConnectedButtonGroup() {
    val options = listOf("Work", "Restaurant", "Coffee")
    val unCheckedIcons =
        listOf(Icons.Outlined.Work, Icons.Outlined.Restaurant, Icons.Outlined.Coffee)
    val checkedIcons = listOf(Icons.Filled.Work, Icons.Filled.Restaurant, Icons.Filled.Coffee)
    var selectedIndex by remember { mutableIntStateOf(0) }
    val haptic = LocalHapticFeedback.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 1.dp, shape = MaterialTheme.shapes.medium),
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
        ) {
            options.forEachIndexed { index, label ->
                val isSelected = selectedIndex == index
                val elevation by animateDpAsState(
                    if (isSelected) 4.dp else 0.dp, label = "elevation"
                )
                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.05f else 1f,
                    animationSpec = tween(durationMillis = 200),
                    label = "scale"
                )

                ToggleButton(
                    checked = isSelected,
                    onCheckedChange = { 
                        selectedIndex = index
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .scale(scale)
                        .shadow(elevation, MaterialTheme.shapes.small)
                        .clearAndSetSemantics {
                            contentDescription =
                                "$label ${if (isSelected) "selected" else "not selected"}"
                        },
                    border = if (isSelected) null else BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Icon(
                        imageVector = if (isSelected) checkedIcons[index] else unCheckedIcons[index],
                        contentDescription = null
                    )
                    Spacer(Modifier.size(ToggleButtonDefaults.IconSpacing))
                    Text(label)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun IconOnlyButtonGroup() {
    val unCheckedIcons =
        listOf(Icons.Outlined.Work, Icons.Outlined.Restaurant, Icons.Outlined.Coffee)
    val checkedIcons = listOf(Icons.Filled.Work, Icons.Filled.Restaurant, Icons.Filled.Coffee)
    val descriptions = listOf("Work", "Restaurant", "Coffee")
    var selectedIndex by remember { mutableIntStateOf(0) }
    val haptic = LocalHapticFeedback.current

    ButtonGroup(
        modifier = Modifier.fillMaxWidth()
    ) {
        unCheckedIcons.forEachIndexed { index, icon ->
            val isSelected = selectedIndex == index
            val iconColor by animateColorAsState(
                if (isSelected) MaterialTheme.colorScheme.primary 
                else MaterialTheme.colorScheme.onSurface,
                label = "iconColor"
            )
            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.15f else 1f,
                animationSpec = tween(durationMillis = 200),
                label = "scale"
            )
            
            IconButton(
                onClick = { 
                    selectedIndex = index
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                },
                modifier = Modifier
                    .scale(scale)
                    .clearAndSetSemantics {
                        contentDescription =
                            "${descriptions[index]} ${if (isSelected) "selected" else "not selected"}"
                    }
            ) {
                Icon(
                    imageVector = if (isSelected) checkedIcons[index] else unCheckedIcons[index],
                    contentDescription = null,
                    tint = iconColor
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MultiSelectButtonGroup() {
    val options = listOf("Option 1", "Option 2", "Option 3")
    var selections by remember { mutableIntStateOf(0b000) } // Using bits to track multiple selections
    val haptic = LocalHapticFeedback.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 2.dp, shape = MaterialTheme.shapes.medium),
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            options.forEachIndexed { index, label ->
                val isSelected = (selections and (1 shl index)) != 0
                val backgroundColor by animateColorAsState(
                    if (isSelected) MaterialTheme.colorScheme.primaryContainer 
                    else MaterialTheme.colorScheme.surface,
                    label = "backgroundColor"
                )
                val textColor by animateColorAsState(
                    if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer 
                    else MaterialTheme.colorScheme.onSurface,
                    label = "textColor"
                )
                val elevation by animateDpAsState(
                    if (isSelected) 2.dp else 0.dp, label = "elevation"
                )

                ToggleButton(
                    checked = isSelected,
                    onCheckedChange = { 
                        selections = selections xor (1 shl index)
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .shadow(elevation, MaterialTheme.shapes.small)
                        .clearAndSetSemantics {
                            contentDescription =
                                "$label ${if (isSelected) "selected" else "not selected"}"
                        }
                ) {
                    Text(
                        text = label,
                        textAlign = TextAlign.Center,
                        color = textColor
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AnimatedButtonGroup() {
    val options = listOf("Stars", "Work", "Food")
    val icons = listOf(Icons.Filled.Star, Icons.Filled.Work, Icons.Filled.Restaurant)
    var selectedIndex by remember { mutableIntStateOf(0) }
    val haptic = LocalHapticFeedback.current

    ButtonGroup(
        modifier = Modifier.fillMaxWidth(),
    ) {
        options.forEachIndexed { index, label ->
            val isSelected = selectedIndex == index
            val containerColor by animateColorAsState(
                if (isSelected) MaterialTheme.colorScheme.primaryContainer 
                else MaterialTheme.colorScheme.surface,
                label = "containerColor"
            )
            val contentColor by animateColorAsState(
                if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer 
                else MaterialTheme.colorScheme.onSurface,
                label = "contentColor"
            )
            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.1f else 1f,
                animationSpec = tween(durationMillis = 200),
                label = "scale"
            )

            FilledIconButton(
                onClick = { 
                    selectedIndex = index
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                },
                modifier = Modifier
                    .weight(1f)
                    .scale(scale)
                    .clearAndSetSemantics {
                        contentDescription =
                            "$label ${if (isSelected) "selected" else "not selected"}"
                    }
            ) {
                Icon(
                    imageVector = icons[index],
                    contentDescription = null,
                    tint = contentColor
                )
            }
        }
    }
}