package com.example.budgettrackerchallenge.di

import android.content.Context
import androidx.room.Room
import com.example.budgettrackerchallenge.data.local.AppDatabase
import com.example.budgettrackerchallenge.data.local.TransactionDao
import com.example.budgettrackerchallenge.data.repository.TransactionRepositoryImpl
import com.example.budgettrackerchallenge.domain.repository.ITransactionRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindModule {
    @Binds
    abstract fun bindTransactionRepository(impl: TransactionRepositoryImpl): ITransactionRepository
}


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "budget_db"
        ).build()
    }

    @Provides
    fun provideTransactionDao(db: AppDatabase): TransactionDao = db.transactionDao()

}