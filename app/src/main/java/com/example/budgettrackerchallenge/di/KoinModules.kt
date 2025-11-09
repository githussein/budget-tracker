package com.example.budgettrackerchallenge.di

import androidx.room.Room
import com.example.budgettrackerchallenge.data.local.AppDatabase
import com.example.budgettrackerchallenge.data.repository.TransactionRepositoryImpl
import com.example.budgettrackerchallenge.domain.repository.ITransactionRepository
import com.example.budgettrackerchallenge.ui.BudgetTrackerScreen.BudgetViewModel
import com.example.budgettrackerchallenge.ui.BudgetTrackerScreen.IBudgetViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    single<ITransactionRepository> { TransactionRepositoryImpl(get()) }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "budget_db"
        ).build()
    }
    
    single { get<AppDatabase>().transactionDao() }
}

val viewModelModule = module {
    viewModel { BudgetViewModel(get()) } bind IBudgetViewModel::class
}