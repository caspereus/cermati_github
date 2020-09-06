package com.cermati.putu.ui.main

import com.cermati.putu.data.models.user.SearchUserResponse

sealed class MainViewState {
    object Loading : MainViewState()
    data class Result(val userResponse: SearchUserResponse) : MainViewState()
    data class Error(val throwable: Throwable) : MainViewState()
}