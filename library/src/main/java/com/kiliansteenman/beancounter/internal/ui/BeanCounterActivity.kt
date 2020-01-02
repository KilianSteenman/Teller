package com.kiliansteenman.beancounter.internal.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kiliansteenman.beancounter.R
import com.kiliansteenman.beancounter.internal.ui.detail.DetailActivity

class BeanCounterActivity : AppCompatActivity(R.layout.beancounter) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = BeanCounterAdapter { logEvent ->
            startActivity(DetailActivity.createIntent(this, logEvent.id))
        }

        findViewById<RecyclerView>(R.id.beancounter_recyclerview).apply {
            this.adapter = adapter
            addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
        }

        val model = ViewModelProviders.of(this)[BeanCounterViewModel::class.java]
        model.logEvents.observe(this, Observer { events -> adapter.logs = events })
    }
}