package com.example.materialdesing3expressive.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.max
import kotlin.math.min

@Composable
fun ColorAdjustableCard(
    modifier: Modifier = Modifier,
    title: String = "Interactive Card",
    initialColor: Color = MaterialTheme.colorScheme.primaryContainer,
    content: @Composable (Color) -> Unit
) {
    var containerColor by remember { mutableStateOf(initialColor) }
    var hue by remember { mutableFloatStateOf(0f) }
    var saturation by remember { mutableFloatStateOf(0.5f) }
    var lightness by remember { mutableFloatStateOf(0.5f) }
    var elevation by remember { mutableFloatStateOf(2f) }
    var cornerRadius by remember { mutableFloatStateOf(0.5f) }

    // Convert HSL to RGB for the card color
    val cardColor = hslToColor(hue, saturation, lightness)
    containerColor = cardColor

    // Determine if we should use light or dark text based on background color
    val contentColor = if (cardColor.luminance() > 0.5f) Color.Black else Color.White

    // Animate changes
    val animatedElevation by animateDpAsState(
        targetValue = elevation.dp * 10,
        animationSpec = tween(300),
        label = "elevation"
    )

    val animatedCornerSize by animateDpAsState(
        targetValue = (cornerRadius * 28).dp,
        animationSpec = tween(300),
        label = "corner"
    )

    Column(modifier = modifier) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = containerColor,
                contentColor = contentColor
            ),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = animatedElevation
            ),
            shape = MaterialTheme.shapes.medium.copy(
                all = androidx.compose.foundation.shape.CornerSize(animatedCornerSize)
            )
        ) {
            Box(
                modifier = Modifier.padding(16.dp)
            ) {
                content(containerColor)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Customize Card",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Hue", style = MaterialTheme.typography.bodyMedium)
            Slider(
                value = hue,
                onValueChange = { hue = it },
                valueRange = 0f..1f,
                modifier = Modifier.fillMaxWidth()
            )

            Text("Saturation", style = MaterialTheme.typography.bodyMedium)
            Slider(
                value = saturation,
                onValueChange = { saturation = it },
                valueRange = 0f..1f,
                modifier = Modifier.fillMaxWidth()
            )

            Text("Lightness", style = MaterialTheme.typography.bodyMedium)
            Slider(
                value = lightness,
                onValueChange = { lightness = it },
                valueRange = 0.1f..0.9f,
                modifier = Modifier.fillMaxWidth()
            )

            Text("Elevation", style = MaterialTheme.typography.bodyMedium)
            Slider(
                value = elevation,
                onValueChange = { elevation = it },
                valueRange = 0f..5f,
                modifier = Modifier.fillMaxWidth()
            )

            Text("Corner Radius", style = MaterialTheme.typography.bodyMedium)
            Slider(
                value = cornerRadius,
                onValueChange = { cornerRadius = it },
                valueRange = 0f..1f,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                "RGB: ${containerColor.toArgb().toHexColor()}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun InteractiveCardContent(bgColor: Color) {
    var scale by remember { mutableFloatStateOf(1f) }
    var alpha by remember { mutableFloatStateOf(1f) }
    val haptic = LocalHapticFeedback.current

    val animatedScale by animateFloatAsState(
        targetValue = scale,
        animationSpec = tween(300),
        label = "scale"
    )

    val animatedAlpha by animateFloatAsState(
        targetValue = alpha,
        animationSpec = tween(300),
        label = "alpha"
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Interactive Card",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Divider()

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Tap and hold the heart to interact with it",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorite",
                modifier = Modifier
                    .size(48.dp)
                    .scale(animatedScale)
                    .alpha(animatedAlpha)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                scale = 1.5f
                                alpha = 0.7f
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                                tryAwaitRelease()

                                scale = 1f
                                alpha = 1f
                            }
                        )
                    },
                tint = if (bgColor.luminance() > 0.5f)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.inversePrimary
            )
        }
    }
}

// Helper function to convert HSL values to Color
fun hslToColor(hue: Float, saturation: Float, lightness: Float): Color {
    // This is a simplified HSL to RGB conversion
    // Hue is in range 0..1 (0° to 360°)
    // Saturation is in range 0..1
    // Lightness is in range 0..1

    val h = hue * 6f
    val s = max(0f, min(1f, saturation))
    val l = max(0f, min(1f, lightness))

    val c = (1f - kotlin.math.abs(2f * l - 1f)) * s
    val x = c * (1f - kotlin.math.abs((h % 2f) - 1f))
    val m = l - c / 2f

    val (r1, g1, b1) = when {
        h < 1f -> Triple(c, x, 0f)
        h < 2f -> Triple(x, c, 0f)
        h < 3f -> Triple(0f, c, x)
        h < 4f -> Triple(0f, x, c)
        h < 5f -> Triple(x, 0f, c)
        else -> Triple(c, 0f, x)
    }

    val red = r1 + m
    val green = g1 + m
    val blue = b1 + m

    return Color(red, green, blue)
}

fun Int.toHexColor(): String {
    return String.format("#%06X", 0xFFFFFF and this)
}

@Preview(showBackground = true)
@Composable
fun ColorAdjustableCardPreview() {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            ColorAdjustableCard { bgColor ->
                InteractiveCardContent(bgColor)
            }
        }
    }
}