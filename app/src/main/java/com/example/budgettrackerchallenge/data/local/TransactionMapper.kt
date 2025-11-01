package com.example.budgettrackerchallenge.data.local

import com.example.budgettrackerchallenge.domain.model.TransactionRecord
import com.example.budgettrackerchallenge.domain.model.TransactionType
import java.time.Instant
import java.time.ZoneId


fun TransactionEntity.toDomain(): TransactionRecord = TransactionRecord(
    id = id,
    type = if (type == "INCOME") TransactionType.INCOME else TransactionType.EXPENSE,
    amount = amount,
    description = description,
    date = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime()
)

fun TransactionRecord.toEntity(): TransactionEntity = TransactionEntity(
    id = id,
    type = if (type == TransactionType.INCOME) "INCOME" else "EXPENSE",
    amount = amount,
    description = description,
    timestamp = date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
)