package com.example.doomscrollingforgodot.doomscroller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.doomscrollingforgodot.data.SpokenLinesRepository
import java.lang.IllegalArgumentException


class DoomScrollerViewModelFactory(
    private val linesRepository: SpokenLinesRepository,
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DoomScrollerViewModel::class.java)) {
            return DoomScrollerViewModel(linesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}