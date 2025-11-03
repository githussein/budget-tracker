package com.example.budgettrackerchallenge.ui.BudgetTrackerScreen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.budgettrackerchallenge.domain.model.TransactionRecord
import com.example.budgettrackerchallenge.ui.components.SwipeToDelete
import com.example.budgettrackerchallenge.ui.components.TransactionListItem

@Composable
fun TransactionsList(
    records: List<TransactionRecord>,
    onDelete: (TransactionRecord) -> Unit
) {
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


@Preview(showBackground = true)
@Composable
fun TransactionsListPreview() {
    TransactionsList(emptyList()) { }
}