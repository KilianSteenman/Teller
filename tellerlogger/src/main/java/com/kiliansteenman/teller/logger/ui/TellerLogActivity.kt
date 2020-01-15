package com.kiliansteenman.teller.logger.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kiliansteenman.teller.logger.R
import com.kiliansteenman.teller.logger.ui.detail.TellerDetailActivity

internal class TellerLogActivity : AppCompatActivity(R.layout.beancounter) {

    private val viewModel: TellerLogViewModel by lazy { ViewModelProviders.of(this)[TellerLogViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = TellerLogAdapter { logEvent ->
            startActivity(TellerDetailActivity.createIntent(this, logEvent.id))
        }

        findViewById<RecyclerView>(R.id.beancounter_recyclerview).apply {
            this.adapter = adapter
            addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
        }

        viewModel.logEvents.observe(this, Observer { events -> adapter.logs = events })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.teller_log, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_teller_log_clear_all -> viewModel.onClearLogClicked()
            R.id.menu_teller_log_filter -> openFilters()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openFilters(): Boolean {
        return true
    }
}