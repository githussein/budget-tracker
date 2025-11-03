package com.example.budgettrackerchallenge.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.budgettrackerchallenge.ui.theme.ExpenseRed
import com.example.budgettrackerchallenge.ui.theme.IncomeGreen
import com.example.budgettrackerchallenge.ui.utils.toCurrency

@Composable
fun IncomeExpensePieChart(
    income: Double,
    expense: Double,
) {
    val total = income + expense

    val animatedIncome by animateFloatAsState(
        targetValue = income.toFloat(),
        animationSpec = tween(durationMillis = 500)
    )

    val animatedExpense by animateFloatAsState(
        targetValue = expense.toFloat(),
        animationSpec = tween(durationMillis = 500)
    )


    val animatedIncomeAngle by animateFloatAsState(
        targetValue = if (total > 0) ((income / total * 360).toFloat()) else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    val animatedExpenseAngle by animateFloatAsState(
        targetValue = if (total > 0) ((expense / total * 360).toFloat()) else 0f,
        animationSpec = tween(durationMillis = 500)
    )


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "Income vs Expenses",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Canvas(modifier = Modifier.size(120.dp)) {
            // income slice
            drawArc(
                color = IncomeGreen,
                startAngle = -90f,
                sweepAngle = animatedIncomeAngle,
                useCenter = true
            )

            // expense slice
            drawArc(
                color = ExpenseRed,
                startAngle = -90f + animatedIncomeAngle,
                sweepAngle = animatedExpenseAngle,
                useCenter = true
            )

            // donut hole
            drawCircle(
                color = Color.White,
                radius = size.minDimension / 4,
                center = center
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            LegendItem(color = IncomeGreen, label = "Income: ${animatedIncome.toDouble().toCurrency()}")
            LegendItem(color = ExpenseRed, label = "Expense: ${animatedExpense.toDouble().toCurrency()}")
        }
    }
}

@Composable
private fun LegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color, shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(label, style = MaterialTheme.typography.bodyMedium)
    }
}