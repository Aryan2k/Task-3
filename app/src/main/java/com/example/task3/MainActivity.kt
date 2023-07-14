package com.example.task3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.task3.ui.theme.Task3Theme

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainActivityViewModel
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

        val lazyListState = rememberLazyListState()
        LazyRow(
            Modifier
                .fillMaxWidth()
                .background(Color.Black),
            state = lazyListState,
            horizontalArrangement = Arrangement.Center
        ) {
            items(items = viewModel.itemList, key = { item -> item.key }) { item ->
                item.LoadProgressBar()
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