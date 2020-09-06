package com.cermati.putu.services.user

import com.cermati.putu.data.models.user.SearchUserResponse
import com.cermati.putu.data.repositories.user.UserRepository
import io.reactivex.Single

class GithubUserServiceImpl(private val userRepository: UserRepository) : UserService {
    override suspend fun searchUser(keyword: String, page: Int): Single<SearchUserResponse> {
        val pageString = page.toString()
        return userRepository.search(keyword, pageString)
    }

}