package com.cermati.putu.data.repositories.user

import com.cermati.putu.data.models.user.SearchUserResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("search/users")
    fun search(
        @Query("q") keyword: String,
        @Query("page") page: String,
        @Query("per_page") perPage: String
    ): Single<SearchUserResponse>

}