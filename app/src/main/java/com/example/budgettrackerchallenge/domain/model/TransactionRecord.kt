package com.example.budgettrackerchallenge.domain.model

import java.time.LocalDateTime
import java.util.UUID

data class TransactionRecord(
    val id: String = UUID.randomUUID().toString(),
    val type: TransactionType,
    val amount: Double,
    val description: String,
    val date: LocalDateTime = LocalDateTime.now()
)
enum class TransactionType {
    INCOME,
    EXPENSE
}

