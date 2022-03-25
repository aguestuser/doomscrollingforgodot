package com.example.doomscrollingforgodot.doomscroller

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            spokenLineList.adapter = SpokenLinesAdapter(viewModel.lines) { viewModel.onScroll(it) }
        }
        return binding.root
    }

    private fun onScroll(lineIdx: Int) {
        Log.i(TAG, "SCROLLED to line $lineIdx")
    }
}