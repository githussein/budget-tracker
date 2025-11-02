package com.example.budgettrackerchallenge.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.budgettrackerchallenge.domain.model.TransactionRecord
import com.example.budgettrackerchallenge.domain.model.TransactionType
import com.example.budgettrackerchallenge.ui.utils.toFormattedString

@Composable
fun TransactionListItem(record: TransactionRecord) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                record.description,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )

            val amountText = if (record.type == TransactionType.EXPENSE)
                "-${record.amount}"
            else
                "+${record.amount}"

            val amountColor = if (record.type == TransactionType.EXPENSE)
                Color(0xFFD32F2F)
            else
                Color(0xFF2E7D32)

            Text(
                amountText,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = amountColor
            )
        }

        Spacer(Modifier.height(4.dp))

        Text(
            text = record.date.toFormattedString(),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )
    }

    HorizontalDivider(
        thickness = 0.6.dp,
        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
    )
}

@Composable
fun <T> SwipeToDelete(
    item: T,
    onDelete: (T) -> Unit,
    content: @Composable () -> Unit
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    val animatedOffsetX by animateFloatAsState(targetValue = offsetX, label = "")

    val swipeThreshold = with(LocalDensity.current) { 80.dp.toPx() } // drag distance to delete

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        if (offsetX < -swipeThreshold) {
                            onDelete(item)
                        } else {
                            offsetX = 0f
                        }
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        offsetX = (offsetX + dragAmount).coerceAtMost(0f) // only left swipe
                    }
                )
            }
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Red.copy(alpha = minOf(-offsetX / swipeThreshold, 1f)))
                .padding(end = 16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = null,
                tint = Color.White
            )
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(animatedOffsetX.toInt(), 0) }
                .background(MaterialTheme.colorScheme.surface)
        ) {
            content()
        }
    }
}