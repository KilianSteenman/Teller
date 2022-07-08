package com.kiliansteenman.teller.logger.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kiliansteenman.teller.logger.R
import com.kiliansteenman.teller.logger.ui.detail.TellerDetailActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class TellerLogActivity : AppCompatActivity(R.layout.teller) {

    private val viewModel: TellerLogViewModel by lazy {
        ViewModelProvider(this)[TellerLogViewModel::class.java]
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
            inflateMenu(R.menu.menu_teller_log)
            setOnMenuItemClickListener { onMenuItemClick(it) }

            (menu.findItem(R.id.menu_teller_log_search).actionView as SearchView).setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        viewModel.onQueryChanged(query)
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        viewModel.onQueryChanged(newText)
                        return true
                    }
                })
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collectLatest { logs -> adapter.logs = logs }
                }
            }
        }
    }

    private fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_teller_log_clear_all -> viewModel.onClearLogClicked()
            R.id.menu_teller_log_search -> openFilters()
            else -> false
        }
    }

    private fun openFilters(): Boolean {
        return true
    }
}
