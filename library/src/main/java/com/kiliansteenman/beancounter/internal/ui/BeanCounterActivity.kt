package com.kiliansteenman.beancounter.internal.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.kiliansteenman.beancounter.R
import com.kiliansteenman.beancounter.internal.data.room.BeanCounterDatabase
import com.kiliansteenman.beancounter.internal.ui.detail.DetailActivity
import java.util.concurrent.Executors


class BeanCounterActivity : AppCompatActivity(R.layout.beancounter) {

    private val db: BeanCounterDatabase
        get() {
            return Room.databaseBuilder(
                applicationContext,
                BeanCounterDatabase::class.java, "database-name"
            ).build()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = BeanCounterAdapter { logEvent ->
            startActivity(DetailActivity.createIntent(this, logEvent.id))
        }

        findViewById<RecyclerView>(R.id.beancounter_recyclerview).apply {
            this.adapter = adapter
            addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
        }

        Executors.newSingleThreadExecutor().execute {
            val logs = db.analyticsLogEventDao().getAll()
            Log.i("BeanCounterActivity", "Getting logs: ${logs.size}")
            runOnUiThread {
                Log.i("BeanCounterActivity", "Setting logs: ${logs.size}")
                adapter.logs = logs
            }
        }
    }
}