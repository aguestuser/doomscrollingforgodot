package com.example.doomscrollingforgodot.doomscroller

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.doomscrollingforgodot.data.SpokenLine
import com.example.doomscrollingforgodot.data.SpokenLinesRepository

class DoomScrollerViewModel(private val linesRepository: SpokenLinesRepository): ViewModel() {
    val TAG = this.javaClass.simpleName

    // PROPERTIES
    val lines: List<SpokenLine> = linesRepository.lines // for now this is static!

    // FUNCTIONS
    fun onScroll(idx: Int) {
        Log.i(TAG, "SCROLLED to line $idx")
    }
}

