package com.kiliansteenman.teller.logger.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kiliansteenman.teller.logger.R
import com.kiliansteenman.teller.logger.data.TellerLog
import java.text.SimpleDateFormat
import java.util.*

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
        private val titleTextView: TextView = itemView.findViewById(R.id.teller_item_title)
        private val contentTextView: TextView = itemView.findViewById(R.id.teller_item_content)
        private val timeStampTextView: TextView = itemView.findViewById(R.id.teller_item_timestamp)

        fun bind(log: TellerLog) {
            frameworkTextView.text = log.framework
            titleTextView.text = log.title
            contentTextView.text = log.content
            timeStampTextView.text = log.logDate.formatTimeStamp()

            itemView.setOnClickListener { onItemClickListener.invoke(log) }
        }

        private fun Long.formatTimeStamp(): String {
            val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            return formatter.format(Date(this))
        }
    }
}