package com.example.budgettrackerchallenge.data.repository

import com.example.budgettrackerchallenge.data.local.TransactionDao
import com.example.budgettrackerchallenge.data.local.toDomain
import com.example.budgettrackerchallenge.data.local.toEntity
import com.example.budgettrackerchallenge.domain.model.TransactionRecord
import com.example.budgettrackerchallenge.domain.model.TransactionType
import com.example.budgettrackerchallenge.domain.repository.ITransactionRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


@OptIn(DelicateCoroutinesApi::class)
class TransactionRepositoryImpl(
    private val dao: TransactionDao
) : ITransactionRepository {

    private val _cache = mutableListOf<TransactionRecord>()
    private val _transactionsFlow = MutableStateFlow<List<TransactionRecord>>(emptyList())

    init {
        dao.getAllTransactions().map { list -> list.map { it.toDomain() } }
            .also { flow ->
                GlobalScope.launch {
                    flow.collect { list ->
                        _cache.clear()
                        _cache.addAll(list)
                        _transactionsFlow.value = _cache.toList()
                    }
                }
            }
    }

    override fun getAllTransactions(): Flow<List<TransactionRecord>> =
        _transactionsFlow

    override fun getTotalIncome(): Flow<Double> =
        _transactionsFlow.map { list ->
            list.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
        }

    override fun getTotalExpense(): Flow<Double> =
        _transactionsFlow.map { list ->
            list.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
        }

    override fun getBalance(): Flow<Double> =
        combine(getTotalIncome(), getTotalExpense()) { income, expense -> income - expense }


    override suspend fun addTransaction(transaction: TransactionRecord) {
        _cache.add(transaction)
        _transactionsFlow.value = _cache.toList()

        dao.insertTransaction(transaction.toEntity())
    }

    override suspend fun deleteTransaction(id: String) {
        _cache.removeAll { it.id == id }
        _transactionsFlow.value = _cache.toList()

        dao.deleteById(id)
    }
}