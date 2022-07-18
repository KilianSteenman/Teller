package com.kiliansteenman.teller.logger.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kiliansteenman.teller.logger.R

internal class TellerParamAdapter : RecyclerView.Adapter<TellerParamAdapter.ParamViewHolder>() {

    private var params: List<Map.Entry<String, String>> = emptyList()

    fun setParameters(params: Map<String, String>) {
        this.params = params.entries.toList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParamViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.teller_param, parent, false)
        return ParamViewHolder(view)
    }

    override fun getItemCount(): Int = params.size

    override fun onBindViewHolder(holder: ParamViewHolder, position: Int) {
        holder.bind(params[position], position)
    }

    inner class ParamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val evenColor =
            itemView.context.resources.getColor(R.color.teller_param_background_even)
        private val oddColor =
            itemView.context.resources.getColor(R.color.teller_param_background_odd)

        private val keyTextView: TextView = itemView.findViewById(R.id.param_key)
        private val valueTextView: TextView = itemView.findViewById(R.id.param_value)

        fun bind(entry: Map.Entry<String, String>, position: Int) {
            keyTextView.text = entry.key
            valueTextView.text = entry.value

            val backgroundColor = if (position.isEven()) evenColor else oddColor
            itemView.setBackgroundColor(backgroundColor)
        }
    }

    private fun Int.isEven(): Boolean = this % 2 == 0
}
