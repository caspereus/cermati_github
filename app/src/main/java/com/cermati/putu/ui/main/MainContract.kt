package com.cermati.putu.ui.main

import androidx.lifecycle.LiveData

interface MainContract {
    val state: LiveData<MainViewState>
    fun searchUser(keyword: String, page: Int)
}