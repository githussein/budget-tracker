package com.example.budgettrackerchallenge.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: String,
    @ColumnInfo val description: String,
    @ColumnInfo val amount: Double,
    @ColumnInfo val type: String,
    @ColumnInfo val timestamp: Long
)