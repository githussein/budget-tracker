package com.example.budgettrackerchallenge

import android.app.Application
import com.example.budgettrackerchallenge.di.databaseModule
import com.example.budgettrackerchallenge.di.repositoryModule
import com.example.budgettrackerchallenge.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class BudgetTracker: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BudgetTracker)
            modules(
                repositoryModule,
                databaseModule,
                viewModelModule
            )
        }
    }
}