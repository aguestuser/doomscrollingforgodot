 package com.example.doomscrollingforgodot.doomscroller

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.ALPHA
import android.view.View.TRANSLATION_Y
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.doomscrollingforgodot.R
import com.example.doomscrollingforgodot.data.SpokenLinesRepository
import com.example.doomscrollingforgodot.databinding.FragmentDoomScrollerBinding
import com.example.doomscrollingforgodot.network.NewsApi
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class DoomScrollerFragment: Fragment() {
    val TAG = this.javaClass.simpleName

    companion object {
        private val imageSources = listOf(
            R.drawable.covid1,
            R.drawable.covid2,
            R.drawable.covid3
        )
        private const val ANIMATION_MIN_SCROLL_IDX = 10 // don't show animation until this scroll

        private const val IMG_DURATION_MIN = 1000 * 60 // animation lasts at least 60s
        private const val IMG_DURATION_RANGE = 1000 * 60 // can be as long as 120s
        private const val IMG_PROBABILITY = .4f
        private const val IMG_ALPHA_MULTIPLIER = .5f
        private const val IMG_SCALE_MULTIPLIER = 5f

        private const val NEWS_DURATION_MIN = 1000 * 30 // animation lasts at least 30s
        private const val NEWS_DURATION_RANGE = 1000 * 30 // can be as long as 60s
        private const val NEWS_PROBABILITY = .2
        private const val NEWS_ALPHA_MULTIPLIER = 2f
        private const val NEWS_SIZE_MIN = 40f // floating text is min 40sp
        private const val NEWS_SIZE_RANGE = 20f // can be as big as 60sp
    }

    private val viewModel by viewModels<DoomScrollerViewModel> {
        DoomScrollerViewModelFactory(
            SpokenLinesRepository.getInstance(requireContext()),
            NewsApi.SERVICE
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
        if (idx < ANIMATION_MIN_SCROLL_IDX) return
        val container = this.requireActivity().findViewById<ViewGroup>(R.id.doom_scroller_container)
        if (Math.random() <= IMG_PROBABILITY) {
            generateImage(container).also { animateImage(it, container) }
        }
        if (Math.random() <= NEWS_PROBABILITY) {
            generateNews(container).also { animateNews(it, container)}
        }
    }

    private fun generateNews(container: ViewGroup): TextView {
        val newsItem = viewModel.takeNewsItem()
        Log.i(TAG, "newsItem.description: ${newsItem.description}")
        return TextView(this.requireContext()).apply {
            text = newsItem.description ?: newsItem.title
            typeface = Typeface.SERIF
            layoutParams = ConstraintLayout.LayoutParams(MATCH_PARENT, 10000)
            textSize = NEWS_SIZE_MIN + Math.random().toFloat() * NEWS_SIZE_RANGE
            textAlignment = View.TEXT_ALIGNMENT_VIEW_END
            alpha = 0f // begin invisible
            translationY = container.height.toFloat() // start at bottom
        }.also {
            container.addView(it)
        }
    }

    private fun animateNews(textView: TextView, container: ViewGroup) {
        val mover = ObjectAnimator.ofFloat(textView, TRANSLATION_Y, -container.height.toFloat())
        val maxAlpha = Math.random().toFloat() * NEWS_ALPHA_MULTIPLIER
        val fader = ObjectAnimator.ofFloat(textView, ALPHA, 5f, maxAlpha, 0f)
        AnimatorSet().apply {
            duration = (NEWS_DURATION_MIN + Math.random() * NEWS_DURATION_RANGE).toLong()
            playTogether(mover, fader)
            removeOnComplete(textView, container)
            start()
        }
    }

    private fun generateImage(container: ViewGroup):  ImageView {
        return AppCompatImageView(this.requireContext()).apply{
            setImageResource(imageSources.random())
            layoutParams = ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            alpha = 0f // begin invisible
            scaleX = Math.random().toFloat() * IMG_SCALE_MULTIPLIER
            scaleY = scaleX
            // place at bottom of screen, randomly spaced btw/ left & right edge of screen
            translationY = container.height.toFloat()
            translationX = (Math.random().toFloat() - .5f) * container.width
        }.also {
            container.addView(it)
        }
    }

    private fun animateImage(imageView: ImageView, container: ViewGroup) {
        // float updward to just above top of screen
        val mover = ObjectAnimator.ofFloat(imageView, TRANSLATION_Y, -container.height.toFloat())
        // fade in and out
        val maxAlpha = Math.random().toFloat() * IMG_ALPHA_MULTIPLIER
        val fader = ObjectAnimator.ofFloat(imageView, ALPHA, 0f, maxAlpha, 0f)
        AnimatorSet().apply{
            duration = (IMG_DURATION_MIN + Math.random() * IMG_DURATION_RANGE).toLong()
            playTogether(mover, fader)
            removeOnComplete(imageView, container)
            start()
        }
    }

    private fun AnimatorSet.removeOnComplete(view: View, container: ViewGroup, ){
        addListener(object: AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator) {
                container.removeView(view)
            }
        })
    }
}