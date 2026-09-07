package com.raihan.assignment.di

import com.raihan.assignment.data.repository.EventRepository
import com.raihan.assignment.data.repository.LoginRepository
import com.raihan.assignment.data.repository.LoginRepositoryImpl
import com.raihan.assignment.data.source.local.AppDatabase
import com.raihan.assignment.data.source.local.database.dao.EventDao
import com.raihan.assignment.data.source.network.service.AssignAppApiService
import com.raihan.assignment.ui.event.EventViewModel
import com.raihan.assignment.ui.login.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {

    private val networkModule =
        module{
            single<AssignAppApiService> { AssignAppApiService.invoke() }
        }

    private val localModule =
        module {
            single<AppDatabase> { AppDatabase.createInstance(androidContext()) }
            single<EventDao> { get<AppDatabase>().eventDao()}
        }

    private val repository =
        module {
            single<LoginRepository> { LoginRepositoryImpl(get()) }
            single { EventRepository(get()) }
        }

    private val viewModel =
        module {
            viewModelOf(::LoginViewModel)
            viewModelOf(::EventViewModel)
        }

    val modules =
        listOf<Module>(
            networkModule,
            localModule,
            repository,
            viewModel
        )
}