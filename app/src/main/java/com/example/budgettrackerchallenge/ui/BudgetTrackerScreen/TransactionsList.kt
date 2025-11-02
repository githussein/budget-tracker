package com.example.budgettrackerchallenge.ui.BudgetTrackerScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.budgettrackerchallenge.domain.model.TransactionRecord
import com.example.budgettrackerchallenge.ui.components.SwipeToDelete
import com.example.budgettrackerchallenge.ui.components.TransactionListItem

@Composable
fun TransactionsList(
    records: List<TransactionRecord>,
    onDelete: (TransactionRecord) -> Unit
) {
    if (records.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No transactions yet",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    } else {
        LazyColumn {
            items(records, key = { it.id }) { record ->
                SwipeToDelete(
                    item = record,
                    onDelete = { onDelete(record) }
                ) {
                    TransactionListItem(record)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TransactionsListPreview() {
    TransactionsList(emptyList()) { }
}