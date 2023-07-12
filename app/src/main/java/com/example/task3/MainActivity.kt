package com.example.task3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.task3.ui.theme.Task3Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Task3Theme {
                DrawUI()
            }
        }
    }
}

@Composable
fun DrawUI() {

    var progress by remember { mutableStateOf(0.0f) }

    val duration = 30 * 60 // 30 minutes in seconds
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                repeat(duration) { iteration ->
                    progress = (iteration + 1).toFloat() / duration
                    delay(1000) // Delay for 1 second
                }
            }
        }
    }

    LoadProgressBar(progress = progress)
}

@Composable
fun LoadProgressBar(progress: Float) {
    LinearProgressIndicator(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .padding(16.dp),
        progress = progress,
        color = Color.Red,
        trackColor = Color.LightGray
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Task3Theme {
        DrawUI()
    }
}