package com.bhanu.club.di

import com.bhanu.club.ui.viewmodel.ClubViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


/**
 * Created by Bhanu Prakash Pasupula on 04,Jun-2020.
 * Email: pasupula1995@gmail.com
 */

val viewModelModules = module {
    viewModel { ClubViewModel(get()) }
}