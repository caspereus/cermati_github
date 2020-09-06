package com.cermati.putu.ui.shared

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

abstract class View : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun showMessage(text: String?) {
        Timber.d(text)
    }
}