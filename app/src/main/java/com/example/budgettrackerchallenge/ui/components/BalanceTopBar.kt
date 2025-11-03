package com.example.budgettrackerchallenge.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.budgettrackerchallenge.R
import com.example.budgettrackerchallenge.ui.utils.toCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalanceTopBar(
    totalBudget: Double,
    isDarkMode: Boolean,
    onToggleDarkMode: () -> Unit
) {

    TopAppBar(
        title = {
            Column {
                Text(stringResource(R.string.balance), style = MaterialTheme.typography.titleMedium)

                Text(
                    text = totalBudget.toCurrency(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        actions = {
            IconButton(onClick = onToggleDarkMode) {
                Text(if (isDarkMode) "☀️" else "🌙")
            }
        }
    )
}