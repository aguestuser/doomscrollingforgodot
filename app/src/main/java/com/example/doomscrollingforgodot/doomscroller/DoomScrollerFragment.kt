package com.example.doomscrollingforgodot.doomscroller

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.doomscrollingforgodot.R
import com.example.doomscrollingforgodot.data.SpokenLinesRepository
import com.example.doomscrollingforgodot.databinding.FragmentDoomScrollerBinding

class DoomScrollerFragment: Fragment() {
    val TAG = this.javaClass.simpleName

    private val viewModel by viewModels<DoomScrollerViewModel> {
        DoomScrollerViewModelFactory(SpokenLinesRepository.getInstance(requireContext()))
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
            it.spokenLineList.adapter = SpokenLinesAdapter(viewModel.lines, viewModel::onScroll)
        }
        return binding.root
    }

    private fun animate() {
        val img = requireActivity().findViewById<ImageView>(R.id.floating_image)
        val startY = viewModel.imgY.value!!
        val endY = startY + 1000f // magic number
        ObjectAnimator.ofFloat(img, View.TRANSLATION_Y, startY, endY).apply {
            duration = 20000
            addUpdateListener {
                Log.i(TAG, "FRAGMENT ANIMATION VALUE: ${it.animatedValue}")
            }
            start()
        }
    }
}