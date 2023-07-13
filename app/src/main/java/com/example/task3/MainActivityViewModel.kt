package com.example.task3

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    lateinit var itemList: SnapshotStateList<MainActivity.ProgressBar>
}