package com.example.budgettrackerchallenge.data.repository

import com.example.budgettrackerchallenge.data.local.TransactionDao
import com.example.budgettrackerchallenge.data.local.toDomain
import com.example.budgettrackerchallenge.data.local.toEntity
import com.example.budgettrackerchallenge.domain.model.TransactionRecord
import com.example.budgettrackerchallenge.domain.repository.ITransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao
) : ITransactionRepository {

    override fun getAllTransactions(): Flow<List<TransactionRecord>> =
        dao.getAllTransactions().map { list -> list.map { it.toDomain() } }
    override fun getTotalIncome(): Flow<Double> =
        dao.getTotalIncome().map { it ?: 0.0 }

    override fun getTotalExpense(): Flow<Double> =
        dao.getTotalExpense().map { it ?: 0.0 }

    override fun getBalance(): Flow<Double> =
        combine(getTotalIncome(), getTotalExpense()) { income, expense ->
            income - expense
        }

    override suspend fun addTransaction(transaction: TransactionRecord) {
        dao.insertTransaction(transaction.toEntity())
    }

    override suspend fun deleteTransaction(id: String) {
        dao.deleteById(id)
    }
}