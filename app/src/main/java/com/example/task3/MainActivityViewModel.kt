package com.example.task3

import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    private val _itemList: List<ProgressBar> = getList().mapIndexed { index, text ->
        val progress = -index.toFloat()
        ProgressBar(progress, text, index)
    }

    val itemList: List<ProgressBar>
        get() = _itemList

    fun nextIteration(key: Int, iteration: Int, duration: Int) {
        _itemList.find { it.key == key }?.let { item ->
            item.progress = (iteration + 1).toFloat() / duration
        }
    }

    fun nextItem() {
        _itemList.map { item -> if (item.progress <= 0f) item.progress++ }
    }

    private fun getList(): List<String> {
        return listOf("My", "Name", "Is", "Aryan", "Mishra")
    }

}