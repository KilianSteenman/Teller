package com.kiliansteenman.teller.logger.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kiliansteenman.teller.logger.R
import com.kiliansteenman.teller.logger.data.RepositoryProvider
import com.kiliansteenman.teller.logger.data.TellerLog
import com.kiliansteenman.teller.logger.formatTimeStamp
import com.kiliansteenman.teller.logger.getShareIntent
import com.kiliansteenman.teller.logger.ui.detail.TellerDetailViewModel.Companion.PARAM_LOG_ID
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class TellerDetailActivity : AppCompatActivity(R.layout.teller_detail) {

    private val viewModel: TellerDetailViewModel by viewModels {
        TellerDetailViewModelFactory(
            repository = RepositoryProvider.getRepository(this),
            owner = this,
            defaultArgs = intent.extras
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<Toolbar>(R.id.detail_toolbar).apply {
            inflateMenu(R.menu.menu_teller_detail)
            setNavigationOnClickListener { onBackPressed() }
            setOnMenuItemClickListener { onMenuItemClick(it) }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.events.collect { onViewEvent(it) } }
                launch { viewModel.state.collectLatest(::onStateChanged) }
            }
        }
    }

    private fun onMenuItemClick(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menu_teller_detail_log_share -> {
            viewModel.onShareLogClicked()
            true
        }
        else -> false
    }

    private fun onStateChanged(state: TellerDetailViewState) {
        when (state) {
            is TellerDetailViewState.Success -> {
                onLogLoaded(state.tellerLog)
            }
            else -> {
                // Do nothing
            }
        }
    }

    private fun onLogLoaded(log: TellerLog) {
        findViewById<TextView>(R.id.detail_title).text = log.title
        findViewById<TextView>(R.id.detail_timestamp).text = log.logDate.formatTimeStamp()
        findViewById<TextView>(R.id.detail_type).text = log.type
        findViewById<TextView>(R.id.detail_content).text = log.content
    }

    private fun onViewEvent(event: TellerEvent) {
        when (event) {
            is TellerEvent.ShareLog -> shareLogsAsText(event.sharableLogs)
        }
    }

    private fun shareLogsAsText(sharableLogs: String) {
        val shareIntent = this@TellerDetailActivity.getShareIntent(
            intentTitle = getString(R.string.share_teller_log_title),
            intentSubject = getString(R.string.share_teller_log_subject),
            content = sharableLogs,
        )
        startActivity(shareIntent)
    }

    companion object {

        fun createIntent(context: Context, logId: Long) =
            Intent(context, TellerDetailActivity::class.java).apply {
                putExtra(PARAM_LOG_ID, logId)
            }
    }
}
