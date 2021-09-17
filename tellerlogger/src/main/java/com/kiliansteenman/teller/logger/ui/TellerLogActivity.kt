package com.kiliansteenman.teller.logger.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kiliansteenman.teller.logger.R
import com.kiliansteenman.teller.logger.ui.detail.TellerDetailActivity

internal class TellerLogActivity : AppCompatActivity(R.layout.teller) {

    private val viewModel: TellerLogViewModel by lazy {
        ViewModelProvider(this).get(TellerLogViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = TellerLogAdapter { logEvent ->
            startActivity(TellerDetailActivity.createIntent(this, logEvent.id))
        }

        findViewById<RecyclerView>(R.id.teller_recyclerview).apply {
            this.adapter = adapter
            addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
        }

        findViewById<Toolbar>(R.id.teller_toolbar).apply {
            inflateMenu(R.menu.teller_log)
            setOnMenuItemClickListener { onMenuItemClick(it) }
        }

        viewModel.logEvents.observe(this) { events -> adapter.logs = events }
    }

    private fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_teller_log_clear_all -> viewModel.onClearLogClicked()
            R.id.menu_teller_log_filter -> openFilters()
            else -> false
        }
    }

    private fun openFilters(): Boolean {
        return true
    }
}
