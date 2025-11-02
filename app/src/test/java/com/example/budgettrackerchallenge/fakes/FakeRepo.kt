package com.example.budgettrackerchallenge.fakes

import com.example.budgettrackerchallenge.domain.model.TransactionRecord
import com.example.budgettrackerchallenge.domain.model.TransactionType
import com.example.budgettrackerchallenge.domain.repository.ITransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class FakeRepo : ITransactionRepository {

    private val _transactions = MutableStateFlow<List<TransactionRecord>>(emptyList())
    override fun getAllTransactions(): Flow<List<TransactionRecord>> = _transactions.asStateFlow()

    override fun getTotalIncome(): Flow<Double> =
        _transactions.map {
            it.filter { t -> t.type == TransactionType.INCOME }.sumOf { t -> t.amount }
        }

    override fun getTotalExpense(): Flow<Double> =
        _transactions.map {
            it.filter { t -> t.type == TransactionType.EXPENSE }.sumOf { t -> t.amount }
        }

    override fun getBalance(): Flow<Double> =
        combine(getTotalIncome(), getTotalExpense()) { income, expense -> income - expense }

    override suspend fun addTransaction(transaction: TransactionRecord) {
        _transactions.value = _transactions.value + transaction
    }

    override suspend fun deleteTransaction(id: String) {
        _transactions.value = _transactions.value.filterNot { it.id == id }
    }
}