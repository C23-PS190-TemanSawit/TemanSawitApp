package com.example.temansawit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.temansawit.network.response.IncomeResponseItem

@Database(
    entities = [IncomeResponseItem::class],
    version = 1,
    exportSchema = false
)
abstract class TemansawitDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: TemansawitDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): TemansawitDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TemansawitDatabase::class.java, "temansawit_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}