package com.kiliansteenman.teller.logger.ui.overview

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kiliansteenman.teller.logger.R
import com.kiliansteenman.teller.logger.data.RepositoryProvider
import com.kiliansteenman.teller.logger.data.TellerLog
import com.kiliansteenman.teller.logger.ui.detail.TellerDetailActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class TellerLogActivity : AppCompatActivity(R.layout.teller) {

    private val viewModel: TellerLogViewModel by viewModels {
        TellerLogViewModelFactory(RepositoryProvider.getRepository(this))
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
                launch { viewModel.state.collectLatest(::onStateChanged) }
            }
        }
    }

    private fun onStateChanged(state: OverViewState) {
        val logsRecyclerView = findViewById<RecyclerView>(R.id.teller_recyclerview)
        val errorMessageView = findViewById<TextView>(R.id.teller_error_message)
        val loadingView =
            findViewById<ContentLoadingProgressBar>(R.id.teller_loading_progressbar)
        val adapter = (logsRecyclerView.adapter as TellerLogAdapter)

        when (state) {
            is OverViewState.Loading -> {
                loadingView.show()
            }
            is OverViewState.Content -> {
                loadingView.hide()
                logsRecyclerView.isVisible = true
                errorMessageView.isVisible = false
                adapter.logs = state.logs
            }
            is OverViewState.Error -> {
                loadingView.hide()
                logsRecyclerView.isVisible = false
                errorMessageView.isVisible = true
                adapter.logs = emptyList()
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

internal sealed class OverViewState {
    object Loading : OverViewState()
    data class Content(val logs: List<TellerLog>) : OverViewState()
    object Error : OverViewState()
}
