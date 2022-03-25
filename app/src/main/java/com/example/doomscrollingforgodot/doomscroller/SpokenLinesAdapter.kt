package com.example.doomscrollingforgodot.doomscroller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.doomscrollingforgodot.R
import com.example.doomscrollingforgodot.data.SpokenLine

class SpokenLinesAdapter(
    private val spokenLines: List<SpokenLine>,
    private val onScroll: (Int) -> Unit
):
    RecyclerView.Adapter<SpokenLinesAdapter.SpokenLineViewHolder>() {

    class SpokenLineViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val speaker: TextView = itemView.findViewById(R.id.speaker_text)
        private val line: TextView = itemView.findViewById(R.id.line_text)

        fun bind(spokenLine: SpokenLine) {
            speaker.text = spokenLine.speaker
            line.text = spokenLine.line
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpokenLineViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.spoken_line_item, parent, false)
        return SpokenLineViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SpokenLineViewHolder, position: Int) {
        onScroll(position)
        holder.bind(spokenLines[position])
    }

    override fun getItemCount(): Int = spokenLines.size
}