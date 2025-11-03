package com.example.budgettrackerchallenge.ui.BudgetTrackerScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgettrackerchallenge.domain.model.TransactionRecord
import com.example.budgettrackerchallenge.domain.repository.ITransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val repository: ITransactionRepository
) : ViewModel(), IBudgetViewModel {

    override val records = repository.getAllTransactions()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    override val totalIncome = repository.getTotalIncome()
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    override val totalExpense = repository.getTotalExpense()
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    override val totalBudget = repository.getBalance()
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    override fun addRecord(record: TransactionRecord) {
        viewModelScope.launch {
            repository.addTransaction(record)
        }
    }

    override fun removeRecord(record: TransactionRecord) {
        viewModelScope.launch {
            repository.deleteTransaction(record.id)
        }
    }
}