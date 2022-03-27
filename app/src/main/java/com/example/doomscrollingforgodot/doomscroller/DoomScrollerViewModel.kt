package com.example.doomscrollingforgodot.doomscroller

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doomscrollingforgodot.R
import com.example.doomscrollingforgodot.data.SpokenLine
import com.example.doomscrollingforgodot.data.SpokenLinesRepository

class DoomScrollerViewModel(private val linesRepository: SpokenLinesRepository): ViewModel() {
    // FIELDS
    private val TAG = this.javaClass.simpleName
    val lines: List<SpokenLine> = linesRepository.lines // for now this is static!
    private val imgSrcs = listOf(R.drawable.covid1, R.drawable.covid2, R.drawable.covid3)

    // LIVE DATA
    private val _imgSrc = MutableLiveData<Int>()
    val imgSrc : LiveData<Int>
        get() = _imgSrc

    private val _imgVisibility = MutableLiveData(View.INVISIBLE)
    val imgVisibility: LiveData<Int>
        get() = _imgVisibility

    private val _imgX = MutableLiveData(0.0f)
    val imgX: LiveData<Float>
        get() = _imgX

    private val _imgY = MutableLiveData(0.0f)
    val imgY: LiveData<Float>
        get() = _imgY

    private val _imgAlpha = MutableLiveData(0.0f)
    val imgAlpha: LiveData<Float>
        get() = _imgAlpha

    private val _imgScale = MutableLiveData(0.0f)
    val imgScale: LiveData<Float>
        get() = _imgScale

    // FUNCTIONS
    fun onScroll(idx: Int) {
        Log.i(TAG, "SCROLLED to line $idx")
        _imgVisibility.value = (if (Math.random() > .5)  View.VISIBLE else View.GONE).also {
            if (it == View.VISIBLE) {
                drawImage()
            }
        }
    }

    private fun drawImage(){
        _imgSrc.value = imgSrcs.random()
        _imgX.value = ((Math.random().toFloat() - .5f) * 1000)
        _imgY.value = (Math.random().toFloat() - .5f) * 1000
        _imgAlpha.value = Math.random().toFloat() * .5f
        _imgScale.value = Math.random().toFloat() // * 50f
    }
}

