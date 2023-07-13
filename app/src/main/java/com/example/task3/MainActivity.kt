package com.example.task3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.task3.ui.theme.Task3Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    lateinit var viewModel: MainActivityViewModel
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

        viewModel = viewModel()

        val list = listOf("My", "Idol", "Is", "Mahendra", "Singh", "Dhoni")
        viewModel.itemList = emptyList<ProgressBar>().toMutableStateList()

        var progress = 0f
        list.forEach { item ->
            viewModel.itemList.add(
                ProgressBar(
                    rememberSaveable { mutableStateOf(progress--) },
                    item
                )
            )
        }

        LazyColumn(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            items(viewModel.itemList) { item ->
                item.LoadProgressBar()
            }
        }
    }

    inner class ProgressBar(private var progress: MutableState<Float>, private var text: String) {

        @Composable
        fun LoadProgressBar() {

            Box(
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp)
            ) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    progress = if (progress.value >= 0f) progress.value else 0f,
                    color = Color.Red,
                    trackColor = Color.LightGray
                )
                Text(text = text, modifier = Modifier.align(Alignment.Center))
            }

            if (progress.value in (0f..1f)) {
                val duration = 5 // 5 seconds per item
                val lifecycleOwner = LocalLifecycleOwner.current

                LaunchedEffect(key1 = lifecycleOwner) {
                    lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        launch {
                            repeat(duration) { iteration ->
                                progress.value = (iteration + 1).toFloat() / duration
                                delay(1000) // Delay for 1 second

                                if (progress.value == 1f) {
                                    viewModel.itemList.map { item -> if (item.progress.value <= 0f) item.progress.value++ }
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