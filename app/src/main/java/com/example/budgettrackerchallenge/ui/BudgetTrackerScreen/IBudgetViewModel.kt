package com.example.budgettrackerchallenge.ui.BudgetTrackerScreen

import com.example.budgettrackerchallenge.domain.model.TransactionRecord
import kotlinx.coroutines.flow.StateFlow

interface IBudgetViewModel {
    val records: StateFlow<List<TransactionRecord>>
    val totalBudget: StateFlow<Double>
    val totalIncome: StateFlow<Double>
    val totalExpense: StateFlow<Double>

    fun addRecord(record: TransactionRecord)
    fun removeRecord(record: TransactionRecord)
}