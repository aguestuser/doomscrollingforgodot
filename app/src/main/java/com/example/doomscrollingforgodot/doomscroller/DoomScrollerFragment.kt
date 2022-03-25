package com.example.doomscrollingforgodot.doomscroller

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.doomscrollingforgodot.R
import com.example.doomscrollingforgodot.data.SpokenLinesRepository
import com.example.doomscrollingforgodot.databinding.FragmentDoomScrollerBinding

class DoomScrollerFragment: Fragment() {

    val TAG = this.javaClass.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val context = requireContext()
        val spokenLinesRepository = SpokenLinesRepository.getInstance(context)
        // TODO: instantiate viewModel if needed here (and
        val spokenLinesAdapter = SpokenLinesAdapter(spokenLinesRepository.lines) { onScroll(it) }

        val binding = DataBindingUtil.inflate<FragmentDoomScrollerBinding>(
            inflater, R.layout.fragment_doom_scroller, container,false
        ).apply {
            // TODO: bind viewModel here if needed
            lifecycleOwner = viewLifecycleOwner
            spokenLineList.adapter = spokenLinesAdapter
        }

        return binding.root
    }

    private fun onScroll(lineIdx: Int) {
        Log.i(TAG, "SCROLLED to line $lineIdx")
    }
}