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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.budgettrackerchallenge.ui.components.BalanceTopBar
import com.example.budgettrackerchallenge.ui.components.IncomeExpensePieChart
import com.example.budgettrackerchallenge.ui.theme.BudgetTrackerChallengeTheme
import com.example.budgettrackerchallenge.ui.theme.ExpenseRed
import com.example.budgettrackerchallenge.ui.theme.IncomeGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: BudgetViewModel = hiltViewModel()
) {
    val records by viewModel.records.collectAsState()
    val totalBudget by viewModel.totalBudget.collectAsState()
    val totalIncome by viewModel.totalIncome.collectAsState()
    val totalExpense by viewModel.totalExpense.collectAsState()

    var showBottomSheet by remember { mutableStateOf(false) }
    var isIncomeSheet by remember { mutableStateOf(true) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var darkModeEnabled by remember { mutableStateOf(false) }


    BudgetTrackerChallengeTheme (darkTheme = darkModeEnabled) {
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
                    modifier = Modifier.fillMaxWidth().padding(end = 8.dp)
                ) {
                    FloatingActionButton(
                        onClick = {
                            isIncomeSheet = true
                            showBottomSheet = true
                        },
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

                TransactionsList(
                    records = records,
                    onDelete = { viewModel.removeRecord(it) }
                )
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
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