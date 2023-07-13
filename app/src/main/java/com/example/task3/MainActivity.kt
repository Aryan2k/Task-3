package com.example.task3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

    private lateinit var a: ProgressBar
    private lateinit var b: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Task3Theme {
                DrawUI()
            }
        }
    }

    @Composable
    fun DrawUI() {

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {

            a = ProgressBar(remember {
                mutableStateOf(0f)
            })

            b = ProgressBar(remember {
                mutableStateOf(-1f)
            })

            a.LoadProgressBar()
            b.LoadProgressBar()

        }
    }

    inner class ProgressBar(var progress: MutableState<Float>) {

        @Composable
        fun LoadProgressBar() {

            LinearProgressIndicator(
                modifier = Modifier
                    .height(200.dp)
                    .width(150.dp)
                    .padding(16.dp),
                progress = if (progress.value >= 0f) progress.value else 0f,
                color = Color.Red,
                trackColor = Color.LightGray
            )

            if (progress.value >= 0f) {
                val duration = 5 // 30 minutes in seconds
                val lifecycleOwner = LocalLifecycleOwner.current

                LaunchedEffect(key1 = lifecycleOwner) {
                    lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        launch {
                            repeat(duration) { iteration ->
                                progress.value = (iteration + 1).toFloat() / duration
                                delay(1000) // Delay for 1 second

                                if (a.progress.value == 1f) {
                                    a.progress.value = -1f
                                    b.progress.value = 0f
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        Task3Theme {
            DrawUI()
        }
    }
}