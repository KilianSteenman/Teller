package com.kiliansteenman.teller.logger.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.kiliansteenman.teller.logger.R
import com.kiliansteenman.teller.logger.assistedViewModelFactory
import com.kiliansteenman.teller.logger.data.TellerLog
import com.kiliansteenman.teller.logger.formatTimeStamp
import com.kiliansteenman.teller.logger.ui.detail.TellerDetailViewModel.Companion.PARAM_LOG_ID

internal class TellerDetailActivity : AppCompatActivity(R.layout.teller_detail) {

    private val viewModel: TellerDetailViewModel by lazy {
        ViewModelProvider(
            owner = this,
            factory = assistedViewModelFactory { savedStateHandle ->
                TellerDetailViewModel(application, savedStateHandle)
            }
        )[TellerDetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<Toolbar>(R.id.detail_toolbar).setNavigationOnClickListener { onBackPressed() }

        viewModel.logEvent.observe(this, ::onLogLoaded)
    }

    private fun onLogLoaded(log: TellerLog) {
        findViewById<TextView>(R.id.detail_title).text = log.title
        findViewById<TextView>(R.id.detail_timestamp).text = log.logDate.formatTimeStamp()
        findViewById<TextView>(R.id.detail_type).text = log.type
        findViewById<TextView>(R.id.detail_content).text = log.content
    }

    companion object {

        fun createIntent(context: Context, logId: Long): Intent {
            return Intent(context, TellerDetailActivity::class.java).apply {
                putExtra(PARAM_LOG_ID, logId)
            }
        }
    }
}
