package com.example.budgettrackerchallenge.fakes

import com.example.budgettrackerchallenge.domain.model.TransactionRecord
import com.example.budgettrackerchallenge.domain.model.TransactionType
import com.example.budgettrackerchallenge.ui.BudgetTrackerScreen.IBudgetViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class FakeBudgetViewModel: IBudgetViewModel {
    override val records = MutableStateFlow<List<TransactionRecord>>(emptyList())
    override val totalBudget = MutableStateFlow(0.0)
    override val totalIncome = MutableStateFlow(0.0)
    override val totalExpense = MutableStateFlow(0.0)

    override fun addRecord(record: TransactionRecord) {
        records.value = records.value + record
        val incomeSum = records.value.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
        val expenseSum = records.value.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
        totalIncome.value = incomeSum
        totalExpense.value = expenseSum
        totalBudget.value = incomeSum - expenseSum
    }

    override fun removeRecord(record: TransactionRecord) {
        records.value = records.value - record
        val incomeSum = records.value.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
        val expenseSum = records.value.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
        totalIncome.value = incomeSum
        totalExpense.value = expenseSum
        totalBudget.value = incomeSum - expenseSum
    }
}