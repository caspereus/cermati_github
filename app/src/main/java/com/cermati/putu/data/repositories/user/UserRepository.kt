package com.cermati.putu.data.repositories.user

import com.cermati.putu.data.models.user.SearchUserResponse
import io.reactivex.Single

interface UserRepository {
    suspend fun search(keyword: String,page : String): Single<SearchUserResponse>
}