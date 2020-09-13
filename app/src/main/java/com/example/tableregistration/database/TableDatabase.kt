package com.example.tableregistration.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tableregistration.dao.CustomerDao
import com.example.tableregistration.dao.TableDao
import com.example.tableregistration.data.Customer
import com.example.tableregistration.data.Table


@Database(version = 2, entities = [Table::class, Customer::class])
abstract class TableDatabase: RoomDatabase() {

    abstract fun getTableDao(): TableDao
    abstract fun getCustomerDao(): CustomerDao

    companion object {
        private var INSTANCE: TableDatabase? = null

        fun getDatabase(context: Context): TableDatabase? {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder<TableDatabase>(
                            context.applicationContext,
                            TableDatabase::class.java,
                            "table_database"
                        ).fallbackToDestructiveMigration().build()
                    }
                }
            }
            return INSTANCE
        }
    }


}