package com.kiliansteenman.beancounter.internal.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.kiliansteenman.beancounter.R
import com.kiliansteenman.beancounter.internal.data.AnalyticsLogEvent

class BeanCounterAdapter : BaseAdapter() {

    var logs: List<AnalyticsLogEvent> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.beancounter_item, parent, false)
        view.findViewById<TextView>(R.id.beancounter_item_title).text = logs[position].title
        view.findViewById<TextView>(R.id.beancounter_item_content).text = logs[position].content
        return view
    }

    override fun getItem(position: Int): Any = logs[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = logs.size
}