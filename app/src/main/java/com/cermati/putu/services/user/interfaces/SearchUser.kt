package com.cermati.putu.services.user.interfaces

import com.cermati.putu.data.models.user.SearchUserResponse
import io.reactivex.Single

interface SearchUser {
    suspend fun searchUser(keyword: String, page: Int): Single<SearchUserResponse>
}