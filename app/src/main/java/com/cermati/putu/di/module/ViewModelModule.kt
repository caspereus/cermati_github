package com.cermati.putu.di.module

import com.cermati.putu.di.module.constants.ModuleConstant
import com.cermati.putu.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(get(named(ModuleConstant.Service.GITHUB_USER_SERVICE)))
    }
}