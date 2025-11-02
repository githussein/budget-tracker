package com.example.budgettrackerchallenge

import com.example.budgettrackerchallenge.domain.model.TransactionRecord
import com.example.budgettrackerchallenge.domain.model.TransactionType
import com.example.budgettrackerchallenge.fakes.FakeRepo
import com.example.budgettrackerchallenge.ui.BudgetTrackerScreen.BudgetViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description


@OptIn(ExperimentalCoroutinesApi::class)
class BudgetViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repo: FakeRepo
    private lateinit var viewModel: BudgetViewModel

    @Before
    fun setup() {
        repo = FakeRepo()
        viewModel = BudgetViewModel(repo)
    }


    @Test
    fun `adding transactions updates list and balance`() = runTest {
        val repo = FakeRepo()
        val viewModel = BudgetViewModel(repo)

        val income = TransactionRecord(
            type = TransactionType.INCOME,
            amount = 100.0,
            description = "Salary"
        )
        val expense = TransactionRecord(
            type = TransactionType.EXPENSE,
            amount = 40.0,
            description = "Groceries"
        )


        viewModel.addRecord(income)
        viewModel.addRecord(expense)

        advanceUntilIdle()

        val records = viewModel.records.first()
        val balance = viewModel.totalBudget.first()

        assertEquals(2, records.size)
        assertEquals(60.0, balance, 0.001)
    }


    @Test
    fun `deleting transaction updates list and balance`() = runTest {
        val item = TransactionRecord(
            type = TransactionType.EXPENSE,
            amount = 50.0,
            description = "Bills"
        )

        viewModel.addRecord(item)
        viewModel.removeRecord(item)

        val records = viewModel.records.first()
        val balance = viewModel.totalBudget.first()

        assertTrue(records.isEmpty())
        assertEquals(0.0, balance, 0.001)
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