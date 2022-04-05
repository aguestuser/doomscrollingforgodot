package com.example.doomscrollingforgodot.doomscroller

import androidx.lifecycle.ViewModel
import com.example.doomscrollingforgodot.data.SpokenLine
import com.example.doomscrollingforgodot.data.SpokenLinesRepository


class DoomScrollerViewModel(
    linesRepository: SpokenLinesRepository,
): ViewModel() {
    val lines: List<SpokenLine> = linesRepository.lines
}

