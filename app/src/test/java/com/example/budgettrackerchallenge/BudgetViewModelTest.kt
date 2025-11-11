package com.example.budgettrackerchallenge

import com.example.budgettrackerchallenge.domain.model.TransactionRecord
import com.example.budgettrackerchallenge.domain.model.TransactionType
import com.example.budgettrackerchallenge.domain.repository.ITransactionRepository
import com.example.budgettrackerchallenge.ui.BudgetTrackerScreen.BudgetViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description


@OptIn(ExperimentalCoroutinesApi::class)
class BudgetViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val transactionsFlow = MutableStateFlow(emptyList<TransactionRecord>())
    private val balanceFlow = MutableStateFlow(0.0)
    private lateinit var viewModel: BudgetViewModel

    @Before
    fun setup() {
        val mockRepo = mockk<ITransactionRepository>()

        every { mockRepo.getAllTransactions() } returns transactionsFlow
        every { mockRepo.getTotalIncome() } returns flowOf(0.0)
        every { mockRepo.getTotalExpense() } returns flowOf(0.0)
        every { mockRepo.getBalance() } returns balanceFlow

        coEvery { mockRepo.addTransaction(any()) } answers {
            transactionsFlow.value += firstArg<TransactionRecord>()
            updateBalance()
        }

        coEvery { mockRepo.deleteTransaction(any()) } answers {
            transactionsFlow.value = transactionsFlow.value.filter { it.id != firstArg<String>() }
            updateBalance()
        }

        viewModel = BudgetViewModel(mockRepo)
    }

    private fun updateBalance() {
        val income = transactionsFlow.value.sumOf { if (it.type == TransactionType.INCOME) it.amount else 0.0 }
        val expense = transactionsFlow.value.sumOf { if (it.type == TransactionType.EXPENSE) it.amount else 0.0 }
        balanceFlow.value = income - expense
    }

    @Test
    fun `adding income increases balance`() = runTest {
        viewModel.addRecord(TransactionRecord(type = TransactionType.INCOME, amount = 200.0, description = "Bonus"))
        advanceUntilIdle()

        assertEquals(200.0, viewModel.totalBudget.first(), 0.001)
    }



    @Test
    fun `adding transactions updates list and balance`() = runTest {
        viewModel.addRecord(TransactionRecord(type = TransactionType.INCOME, amount = 100.0, description = "Salary"))
        viewModel.addRecord(TransactionRecord(type = TransactionType.EXPENSE, amount = 40.0, description = "Groceries"))
        advanceUntilIdle()

        assertEquals(2, viewModel.records.first().size)
        assertEquals(60.0, viewModel.totalBudget.first(), 0.001)
    }

    @Test
    fun `deleting transaction updates list and balance`() = runTest {
        val item = TransactionRecord(type = TransactionType.EXPENSE, amount = 50.0, description = "Bills")

        viewModel.addRecord(item)
        viewModel.removeRecord(item)
        advanceUntilIdle()

        assertTrue(viewModel.records.first().isEmpty())
        assertEquals(0.0, viewModel.totalBudget.first(), 0.001)
    }
}


@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }

}