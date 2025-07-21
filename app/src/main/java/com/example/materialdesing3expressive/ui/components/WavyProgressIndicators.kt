package com.example.materialdesing3expressive.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ExpressiveProgressIndicators(
    modifier: Modifier = Modifier
) {
    var progress by remember { mutableFloatStateOf(0.65f) }
    var showIndeterminate by remember { mutableStateOf(false) }
    var autoProgress by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1000),
        label = "progress"
    )
    
    // Auto progress with coroutines
    LaunchedEffect(autoProgress) {
        if (autoProgress && !showIndeterminate) {
            while (autoProgress) {
                coroutineScope.launch {
                    var currentProgress = 0f
                    while (currentProgress <= 1f && autoProgress) {
                        progress = currentProgress
                        delay(50)
                        currentProgress += 0.01f
                    }
                    if (autoProgress) {
                        delay(500)
                        progress = 0f
                    }
                }
                delay(2000)
            }
        }
    }

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Expressive Progress Indicators",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        if (showIndeterminate) {
            Text(
                text = "Indeterminate Linear Progress",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Indeterminate Circular Progress",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Text(
                text = "Determinate Linear Progress",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Progress: ${(progress * 100).roundToInt()}%",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Determinate Circular Progress",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    progress = { animatedProgress }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (showIndeterminate) "Indeterminate" else "Determinate",
                    style = MaterialTheme.typography.bodyLarge
                )
                Switch(
                    checked = showIndeterminate,
                    onCheckedChange = { 
                        showIndeterminate = it
                        if (it) autoProgress = false
                    }
                )
            }
            
            if (!showIndeterminate) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Auto Progress",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Switch(
                        checked = autoProgress,
                        onCheckedChange = { autoProgress = it }
                    )
                }
                
                if (!autoProgress) {
                    Text(
                        text = "Progress",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                    Slider(
                        value = progress,
                        onValueChange = { progress = it },
                        valueRange = 0f..1f,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpressiveProgressIndicatorsPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            ExpressiveProgressIndicators()
        }
    }
}