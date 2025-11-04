package com.example.budgettrackerchallenge.ui.BudgetTrackerScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.budgettrackerchallenge.domain.model.TransactionRecord
import com.example.budgettrackerchallenge.domain.model.TransactionType
import com.example.budgettrackerchallenge.ui.BudgetTrackerScreen.components.RecordsHeader
import com.example.budgettrackerchallenge.ui.components.BalanceTopBar
import com.example.budgettrackerchallenge.ui.components.EmptyTransactionsView
import com.example.budgettrackerchallenge.ui.components.IncomeExpensePieChart
import com.example.budgettrackerchallenge.ui.components.RecordSearchField
import com.example.budgettrackerchallenge.ui.theme.BudgetTrackerChallengeTheme
import com.example.budgettrackerchallenge.ui.theme.ExpenseRed
import com.example.budgettrackerchallenge.ui.theme.IncomeGreen
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: IBudgetViewModel = hiltViewModel<BudgetViewModel>()
) {
    val records by viewModel.records.collectAsState()
    val totalBudget by viewModel.totalBudget.collectAsState()
    val totalIncome by viewModel.totalIncome.collectAsState()
    val totalExpense by viewModel.totalExpense.collectAsState()

    var showBottomSheet by remember { mutableStateOf(false) }
    var isIncomeSheet by remember { mutableStateOf(true) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var searchQuery by remember { mutableStateOf("") }
    var filter by remember { mutableStateOf<TransactionType?>(null) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    val filteredRecords = records
        .filter { record ->
            when (filter) {
                TransactionType.INCOME -> record.type == TransactionType.INCOME
                TransactionType.EXPENSE -> record.type == TransactionType.EXPENSE
                else -> true
            }
        }
        .filter { record ->
            record.description.contains(searchQuery, ignoreCase = true)
        }
        .filter { record ->
            selectedDate?.let { record.date.toLocalDate() == it } ?: true
        }

    val uiState = remember(filteredRecords) {
        if (filteredRecords.isEmpty()) {
            TransactionUiState.Empty
        } else {
            TransactionUiState.HasData(filteredRecords)
        }
    }

    var darkModeEnabled by remember { mutableStateOf(false) }


    BudgetTrackerChallengeTheme(darkTheme = darkModeEnabled) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    BalanceTopBar(
                        totalBudget = totalBudget,
                        isDarkMode = darkModeEnabled,
                        onToggleDarkMode = { darkModeEnabled = !darkModeEnabled }
                    )
                },
                floatingActionButton = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp)
                    ) {
                        FloatingActionButton(
                            onClick = {
                                isIncomeSheet = true
                                showBottomSheet = true
                            },
                            modifier = Modifier.testTag("IncomeFAB"),
                            containerColor = IncomeGreen,
                            contentColor = Color.White,
                            shape = CircleShape
                        ) {
                            Text("＋", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        FloatingActionButton(
                            onClick = {
                                isIncomeSheet = false
                                showBottomSheet = true
                            },
                            modifier = Modifier.testTag("ExpenseFAB"),
                            containerColor = ExpenseRed,
                            contentColor = Color.White,
                            shape = CircleShape
                        ) {
                            Text("—", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    IncomeExpensePieChart(income = totalIncome, expense = totalExpense)

                    RecordsHeader(
                        filter = filter,
                        onFilterChange = { filter = it },
                        selectedDate = selectedDate,
                        onDateSelected = { selectedDate = it }
                    )

                    RecordSearchField(query = searchQuery) { searchQuery = it }


                    when (uiState) {
                        is TransactionUiState.Empty -> {
                            EmptyTransactionsView()
                        }

                        is TransactionUiState.HasData -> {
                            TransactionsList(
                                records = uiState.records,
                                onDelete = { viewModel.removeRecord(it) }
                            )
                        }
                    }
                }
            }

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = sheetState,
                    modifier = Modifier.testTag("AddTransactionSheet")
                ) {
                    AddTransactionBottomSheet(
                        onDismiss = { showBottomSheet = false },
                        onSubmit = { newRecord -> viewModel.addRecord(newRecord) },
                        isIncome = isIncomeSheet
                    )
                }
            }
        }
    }
}

sealed class TransactionUiState {
    object Empty : TransactionUiState()
    data class HasData(val records: List<TransactionRecord>) : TransactionUiState()
}