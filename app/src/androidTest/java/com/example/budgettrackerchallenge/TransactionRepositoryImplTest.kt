package com.example.budgettrackerchallenge

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.budgettrackerchallenge.data.local.AppDatabase
import com.example.budgettrackerchallenge.data.local.TransactionDao
import com.example.budgettrackerchallenge.data.repository.TransactionRepositoryImpl
import com.example.budgettrackerchallenge.domain.model.TransactionRecord
import com.example.budgettrackerchallenge.domain.model.TransactionType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TransactionRepositoryImplTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: TransactionDao
    private lateinit var repository: TransactionRepositoryImpl

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.transactionDao()
        repository = TransactionRepositoryImpl(dao)
    }

    @After
    fun tearDown() {
        db.close()
    }


    @Test
    fun `add transaction and get all transactions`() = runBlocking {
        val record = TransactionRecord(description = "Test Income", amount =  100.0, type =  TransactionType.INCOME)
        repository.addTransaction(record)

        val all = repository.getAllTransactions().first()
        Assert.assertEquals(1, all.size)
        Assert.assertEquals(record.description, all[0].description)
        Assert.assertEquals(record.amount, all[0].amount, 0.001)
        Assert.assertEquals(record.type, all[0].type)
    }

}