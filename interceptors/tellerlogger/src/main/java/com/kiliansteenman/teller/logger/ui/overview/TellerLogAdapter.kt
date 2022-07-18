package com.kiliansteenman.teller.logger.ui.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kiliansteenman.teller.logger.R
import com.kiliansteenman.teller.logger.data.TellerLog
import com.kiliansteenman.teller.logger.formatTimeStamp
import com.kiliansteenman.teller.logger.prettyFormat

internal class TellerLogAdapter(
    private val onItemClickListener: (TellerLog) -> Unit
) : RecyclerView.Adapter<TellerLogAdapter.TellerItemViewHolder>() {

    var logs: List<TellerLog> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TellerItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.teller_item, parent, false)
        return TellerItemViewHolder(view)
    }

    override fun getItemCount(): Int = logs.size

    override fun onBindViewHolder(holder: TellerItemViewHolder, position: Int) {
        holder.bind(logs[position])
    }

    inner class TellerItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val frameworkTextView: TextView = itemView.findViewById(R.id.teller_framework)
        private val logTypeTextView: TextView = itemView.findViewById(R.id.teller_log_type)
        private val titleTextView: TextView = itemView.findViewById(R.id.teller_item_title)
        private val paramsTextView: TextView = itemView.findViewById(R.id.teller_item_params)
        private val timeStampTextView: TextView = itemView.findViewById(R.id.teller_item_timestamp)

        fun bind(log: TellerLog) {
            frameworkTextView.text = log.framework
            logTypeTextView.text = log.type
            titleTextView.text = log.title
            paramsTextView.text = log.params.prettyFormat()
            timeStampTextView.text = log.logDate.formatTimeStamp()

            itemView.setOnClickListener { onItemClickListener.invoke(log) }
        }
    }
}
