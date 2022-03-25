package com.example.doomscrollingforgodot.doomscroller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.doomscrollingforgodot.R
import com.example.doomscrollingforgodot.data.SpokenLinesRepository
import com.example.doomscrollingforgodot.databinding.FragmentDoomScrollerBinding

class DoomScrollerFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // return super.onCreateView(inflater, container, savedInstanceState)

        val context = requireContext()
        val spokenLinesRepository = SpokenLinesRepository.getInstance(context)
        val lines = spokenLinesRepository.lines
        // TODO: instantiate viewModel if needed here

        val binding = DataBindingUtil.inflate<FragmentDoomScrollerBinding>(
            inflater, R.layout.fragment_doom_scroller, container,false
        ).also {
            // TODO: bind viewModel here if needed
            it.lifecycleOwner = viewLifecycleOwner
            it.spokenLineList.adapter = SpokenLinesAdapter(lines)
        }

        return binding.root
    }
}