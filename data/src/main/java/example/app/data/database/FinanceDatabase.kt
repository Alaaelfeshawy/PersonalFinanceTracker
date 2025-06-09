package example.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import example.app.data.dao.TransactionDao
import example.app.data.entity.TransactionEntity

@Database(entities = [TransactionEntity::class], version = 1, exportSchema = false)
abstract class FinanceDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}
