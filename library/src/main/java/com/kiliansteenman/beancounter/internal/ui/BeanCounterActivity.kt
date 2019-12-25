package com.kiliansteenman.beancounter.internal.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.kiliansteenman.beancounter.R
import com.kiliansteenman.beancounter.internal.data.room.BeanCounterDatabase
import java.util.concurrent.Executors

class BeanCounterActivity : Activity() {

    private val db: BeanCounterDatabase
        get() {
            return Room.databaseBuilder(
                applicationContext,
                BeanCounterDatabase::class.java, "database-name"
            ).build()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.beancounter)

        val adapter = BeanCounterAdapter()
        findViewById<RecyclerView>(R.id.beancounter_recyclerview).adapter = adapter

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