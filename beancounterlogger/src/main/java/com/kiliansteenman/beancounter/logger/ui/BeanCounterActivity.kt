package com.kiliansteenman.beancounter.logger.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kiliansteenman.beancounter.R
import com.kiliansteenman.beancounter.logger.ui.detail.DetailActivity

class BeanCounterActivity : AppCompatActivity(R.layout.beancounter) {

    private val viewModel: BeanCounterViewModel by lazy { ViewModelProviders.of(this)[BeanCounterViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = BeanCounterAdapter { logEvent ->
            startActivity(DetailActivity.createIntent(this, logEvent.id))
        }

        findViewById<RecyclerView>(R.id.beancounter_recyclerview).apply {
            this.adapter = adapter
            addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
        }

        viewModel.logEvents.observe(this, Observer { events -> adapter.logs = events })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.beancounter_log, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_beancounter_log_clear_all -> viewModel.onClearLogClicked()
            R.id.menu_beancounter_log_filter -> openFilters()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openFilters(): Boolean {
        return true
    }
}