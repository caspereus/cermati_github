package com.cermati.putu.di.module

import com.cermati.putu.data.repositories.user.UserRepository
import com.cermati.putu.data.repositories.user.impl.GithubUserRepositoryImpl
import com.cermati.putu.di.module.constants.ModuleConstant
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single(named(ModuleConstant.Repository.GITHUB_USER_REPOSITORY)) {
        GithubUserRepositoryImpl(get()) as UserRepository
    }
}