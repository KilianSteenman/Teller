package com.kiliansteenman.beancounter.logger.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kiliansteenman.beancounter.R
import com.kiliansteenman.beancounter.logger.data.AnalyticsLogEvent
import java.text.SimpleDateFormat
import java.util.*

class BeanCounterAdapter(
    private val onItemClickListener: (AnalyticsLogEvent) -> Unit
) : RecyclerView.Adapter<BeanCounterAdapter.BeanCounterViewHolder>() {

    var logs: List<AnalyticsLogEvent> = emptyList()
        set(value) {
            field = value.reversed()
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeanCounterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.beancounter_item, parent, false)
        return BeanCounterViewHolder(view)
    }

    override fun getItemCount(): Int = logs.size

    override fun onBindViewHolder(holder: BeanCounterViewHolder, position: Int) {
        holder.bind(logs[position])
    }

    inner class BeanCounterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val typeView: TextView = itemView.findViewById(R.id.beancounter_item_type)
        private val titleView: TextView = itemView.findViewById(R.id.beancounter_item_title)
        private val contentView: TextView = itemView.findViewById(R.id.beancounter_item_content)
        private val timeStampView: TextView = itemView.findViewById(R.id.beancounter_item_timestamp)

        fun bind(logEvent: AnalyticsLogEvent) {
            typeView.text = logEvent.type
            titleView.text = logEvent.title
            contentView.text = logEvent.content
            timeStampView.text = logEvent.logDate.formatTimeStamp()

            itemView.setOnClickListener { onItemClickListener.invoke(logEvent) }
        }

        private fun Long.formatTimeStamp(): String {
            val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            return formatter.format(Date(this))
        }
    }
}