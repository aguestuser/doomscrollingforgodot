package com.example.doomscrollingforgodot.doomscroller

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.ALPHA
import android.view.View.TRANSLATION_Y
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.doomscrollingforgodot.R
import com.example.doomscrollingforgodot.data.SpokenLinesRepository
import com.example.doomscrollingforgodot.databinding.FragmentDoomScrollerBinding
import com.example.doomscrollingforgodot.network.NewsApi

class DoomScrollerFragment: Fragment() {
    val TAG = this.javaClass.simpleName

    companion object {
        private val imageSources = listOf(
            R.drawable.covid1,
            R.drawable.covid2,
            R.drawable.covid3
        )
        private const val ANIMATION_DURATION_MEAN = 1000 * 60 // likely animation duration = 20
        private const val ANIMATION_DURATION_VARIANCE = 1000 * 20 // varying upward by +/- 2s
        private const val ANIMATION_DURATION_MIN = ANIMATION_DURATION_MEAN - ANIMATION_DURATION_VARIANCE / 2
        private const val IMG_PROBABILITY = .2f
        private const val IMG_ALPHA_MULTIPLIER = .5f
        private const val IMG_SCALE_MULTIPLIER = 2f
    }

    private val viewModel by viewModels<DoomScrollerViewModel> {
        DoomScrollerViewModelFactory(
            SpokenLinesRepository.getInstance(requireContext()),
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = DataBindingUtil.inflate<FragmentDoomScrollerBinding>(
            inflater, R.layout.fragment_doom_scroller, container,false
        ).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.vm = viewModel
            it.spokenLineList.adapter = SpokenLinesAdapter(viewModel.lines, this::onScroll)
        }
        return binding.root
    }

    private fun onScroll(idx: Int) {
        Log.i(TAG, "SCROLL to line $idx")
        val container = this.requireActivity().findViewById<ViewGroup>(R.id.doom_scroller_container)
        if (Math.random() <= IMG_PROBABILITY) {
            generateFloatingImage(container).also { animateImage(it, container) }
        }
    }

    private fun generateFloatingImage(container: ViewGroup):  ImageView {
        return AppCompatImageView(this.requireContext()).apply{
            setImageResource(imageSources.random())
            layoutParams = ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            alpha = 0f // begin invisible
            scaleX = Math.random().toFloat() * IMG_SCALE_MULTIPLIER
            scaleY = scaleX
            // place at bottom of screen, randomly spaced btw/ left & right edge of screen
            translationX = (Math.random().toFloat() - .5f) * container.width
            translationY = container.height.toFloat()
        }.also {
            container.addView(it)
        }
    }

    private fun animateImage(floatingImage: ImageView, container: ViewGroup) {
        // float updward to just above top of screen
        val mover = ObjectAnimator.ofFloat(
            floatingImage,
            TRANSLATION_Y,
            -(container.height + floatingImage.height).toFloat()
        )
        // fade in and out
        val fader = ObjectAnimator.ofFloat(
            floatingImage,
            ALPHA,
            0f, Math.random().toFloat() * IMG_ALPHA_MULTIPLIER, 0f,
        )
        AnimatorSet().apply{
            duration = (Math.random() * ANIMATION_DURATION_VARIANCE + ANIMATION_DURATION_MIN).toLong()
            playTogether(mover, fader)
            start()
        }
    }

    private fun AnimatorSet.removeOnComplete(container: ViewGroup, view: View){
        addListener(object: AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator) {
                container.removeView(view)
            }
        })
    }
}