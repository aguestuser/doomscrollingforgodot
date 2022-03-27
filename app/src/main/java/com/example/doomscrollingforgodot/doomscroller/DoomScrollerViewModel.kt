package com.example.doomscrollingforgodot.doomscroller

import android.animation.ValueAnimator
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
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

    private val _shouldAnimate = MutableLiveData<Boolean>(false)
    val shouldAnimate : LiveData<Boolean>
        get() = _shouldAnimate

    // FUNCTIONS
    fun onScroll(idx: Int) {
        Log.i(TAG, "SCROLLED to line $idx")
        val imageVisibilityProbability = .8
        if (Math.random() < imageVisibilityProbability) {
            _imgVisibility.value = View.VISIBLE
            drawImage()
        } else {
            _imgVisibility.value = View.INVISIBLE
        }
    }

    private fun drawImage(){
        _imgSrc.value = imgSrcs.random()
        _imgX.value = ((Math.random().toFloat() - .5f) * 1000)
        _imgScale.value = Math.random().toFloat() // * 50f
        animateY()
        animateAlpha()
    }

    private fun animateY() {
        val riseDistance = 1000f
        val start = (Math.random().toFloat() - .5f) * 1000
        val end = start - riseDistance
        ValueAnimator.ofFloat(start, end).apply {
            interpolator = LinearInterpolator()
            duration = 20000
            addUpdateListener {
                _imgY.value = it.animatedValue as Float
            }
            start()
        }
    }

    private fun animateAlpha() {
        val start = 0f
        val max = Math.random().toFloat() //* .5f
        ValueAnimator.ofFloat(start, max, start).apply {
            // interpolator = LinearInterpolator()
            duration = 20000
            addUpdateListener {
                _imgAlpha.value = it.animatedValue as Float
            }
            start()
        }
    }
}

