package com.cermati.putu.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cermati.putu.CermatiApplication
import com.cermati.putu.data.models.user.DataUser
import com.cermati.putu.services.user.UserService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainViewModel(private val userService: UserService) : ViewModel(),
    CoroutineScope,
    MainContract {

    private val mainStateObserver: MutableLiveData<MainViewState> by lazy {
        MutableLiveData<MainViewState>()
    }

    override val coroutineContext: CoroutineContext get() = Dispatchers.Main
    override val state: LiveData<MainViewState> get() = mainStateObserver

    override fun searchUser(keyword: String, page: Int) {
        launch {
            userService.searchUser(keyword, page)
                .map<MainViewState>(MainViewState::Result)
                .onErrorReturn(MainViewState::Error)
                .toFlowable()
                .startWith(MainViewState.Loading)
                .subscribe(mainStateObserver::postValue)
        }

    }
}