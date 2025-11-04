package com.example.budgettrackerchallenge.ui.BudgetTrackerScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.budgettrackerchallenge.R
import com.example.budgettrackerchallenge.domain.model.TransactionType
import com.example.budgettrackerchallenge.ui.components.DatePickerDialog
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordsHeader(
    filter: TransactionType?,
    onFilterChange: (TransactionType?) -> Unit,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate?) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.records),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
            ) {
            OutlinedButton(
                onClick = {
                    if (selectedDate != null) {
                        onDateSelected(null)
                    } else {
                        showDatePicker = true
                    }
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (selectedDate != null) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    else MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier.size(40.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "Select date",
                    tint = if (selectedDate != null) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface
                )
            }

            FilterChip(
                selected = filter == TransactionType.INCOME,
                onClick = {
                    onFilterChange(if (filter == TransactionType.INCOME) null else TransactionType.INCOME)
                },
                label = { Text(stringResource(R.string.income)) }
            )

            FilterChip(
                selected = filter == TransactionType.EXPENSE,
                onClick = {
                    onFilterChange(if (filter == TransactionType.EXPENSE) null else TransactionType.EXPENSE)
                },
                label = { Text(stringResource(R.string.expense)) }
            )
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            onDateSelected = { date ->
                onDateSelected(date)
                showDatePicker = false
            }
        )
    }
}