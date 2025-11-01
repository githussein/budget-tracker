package com.example.budgettrackerchallenge.ui.BudgetTrackerScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.budgettrackerchallenge.ui.components.BudgetTopBar
import com.example.budgettrackerchallenge.ui.theme.BudgetTrackerChallengeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: BudgetViewModel = hiltViewModel()
) {
    val records by viewModel.records.collectAsState()
    val totalBudget by viewModel.totalBudget.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    BudgetTrackerChallengeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                BudgetTopBar(
                    totalBudget = totalBudget,
                    onAddClick = { showBottomSheet = true }
                )
            }
        ) { innerPadding ->
            TransactionsList(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                records = records,
                onDelete = { viewModel.removeRecord(it) }
            )
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ) {
                AddTransactionBottomSheet(
                    onDismiss = { showBottomSheet = false },
                    onSubmit = { newRecord -> viewModel.addRecord(newRecord) }
                )
            }
        }
    }
}