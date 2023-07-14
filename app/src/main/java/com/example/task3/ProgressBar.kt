package com.example.task3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressBar(
    initialProgressValue: Float,
    private var text: String,
    var key: Int
) {
    private var value = initialProgressValue
    private lateinit var viewModel: MainActivityViewModel
    var progress: Float by mutableStateOf(initialProgressValue)

    @Composable
    fun LoadProgressBar() {

        viewModel = viewModel()

        Box(
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                progress = if (progress >= 0f) progress else 0f,
                color = Color.Red,
                trackColor = Color.LightGray
            )
            Text(text = text, modifier = Modifier.align(Alignment.Center))
        }

        if (progress in 0f..1f) {
            val duration = 5  // 5 seconds per item
            val coroutineScope = rememberCoroutineScope()
            if (progress < 1f) {
                LaunchedEffect(Unit) {
                    coroutineScope.launch {
                        repeat(duration) { iteration ->
                            viewModel.nextIteration(key, iteration, duration)
                            delay(1000) // Delay for 1 second
                        }
                        if (progress == 1f)
                            viewModel.nextItem()
                    }
                }
            }
        }
    }
}