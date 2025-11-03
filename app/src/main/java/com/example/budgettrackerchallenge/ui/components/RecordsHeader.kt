package com.example.budgettrackerchallenge.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.budgettrackerchallenge.R
import com.example.budgettrackerchallenge.domain.model.TransactionType

@Composable
fun RecordsHeader(
    filter: TransactionType?,
    onFilterChange: (TransactionType?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.records),
            style = MaterialTheme.typography.titleLarge
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = filter == TransactionType.INCOME,
                onClick = {
                    onFilterChange(
                        if (filter == TransactionType.INCOME) null else TransactionType.INCOME
                    )
                },
                label = { Text(stringResource(R.string.income)) }
            )

            FilterChip(
                selected = filter == TransactionType.EXPENSE,
                onClick = {
                    onFilterChange(
                        if (filter == TransactionType.EXPENSE) null else TransactionType.EXPENSE
                    )
                },
                label = { Text(stringResource(R.string.expense)) }
            )
        }
    }
}