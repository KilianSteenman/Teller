package com.kiliansteenman.teller.logger

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

internal inline fun <reified T : ViewModel> AppCompatActivity.assistedViewModelFactory(
    crossinline viewModelProducer: (SavedStateHandle) -> T
): AbstractSavedStateViewModelFactory =
    object : AbstractSavedStateViewModelFactory(this, intent.extras) {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ) = viewModelProducer(handle) as T
    }
