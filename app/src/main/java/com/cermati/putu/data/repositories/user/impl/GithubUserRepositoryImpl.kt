package com.cermati.putu.data.repositories.user.impl

import com.cermati.putu.data.models.user.SearchUserResponse
import com.cermati.putu.data.repositories.user.UserApi
import com.cermati.putu.data.repositories.user.UserRepository
import io.reactivex.Single

class GithubUserRepositoryImpl(private val userApi: UserApi) : UserRepository {


    override suspend fun search(keyword: String, page: String): Single<SearchUserResponse> {
        return userApi.search(keyword, page, PER_PAGE);
    }

    companion object {
        const val PER_PAGE = "10"
    }
}