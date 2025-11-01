package com.example.budgettrackerchallenge.domain.repository

import com.example.budgettrackerchallenge.domain.model.TransactionRecord
import kotlinx.coroutines.flow.Flow

interface ITransactionRepository {
    fun getAllTransactions(): Flow<List<TransactionRecord>>
    fun getTotalIncome(): Flow<Double>
    fun getTotalExpense(): Flow<Double>
    fun getBalance(): Flow<Double>
    suspend fun addTransaction(transaction: TransactionRecord)
    suspend fun deleteTransaction(id: String)
}