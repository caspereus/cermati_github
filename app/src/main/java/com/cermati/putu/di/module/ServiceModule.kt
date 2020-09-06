package com.cermati.putu.di.module

import com.cermati.putu.di.module.constants.ModuleConstant
import com.cermati.putu.services.user.GithubUserServiceImpl
import com.cermati.putu.services.user.UserService
import org.koin.core.qualifier.named
import org.koin.dsl.module

val serviceModule = module {

    single(named(ModuleConstant.Service.GITHUB_USER_SERVICE)) {
        GithubUserServiceImpl(get(named(ModuleConstant.Repository.GITHUB_USER_REPOSITORY))) as UserService
    }

}