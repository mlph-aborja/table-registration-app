package com.example.tableregistration.repository

import android.app.Application
import android.util.Log
import com.example.tableregistration.constant.LOG_INIT_TABLE
import com.example.tableregistration.data.Table
import com.example.tableregistration.data.TableWithCustomers
import com.example.tableregistration.database.TableDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TableRepository(val app: Application) {

    private val tableDao = TableDatabase.getDatabase(app)!!.getTableDao()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            Log.i(LOG_INIT_TABLE, "init: Table")
            for (i in 1..4) {
                val table = tableDao.findById(i)
                if (table == null) {
                    tableDao.save(Table(i, "Table $i"))
                }
            }
        }
    }

    fun findAllTablesWithCustomer(): List<TableWithCustomers> {
        return tableDao.findTablesWithCustomers()
    }

    fun findTableWithCustomerById(Id: Int): TableWithCustomers {
        return tableDao.findTableWithCustomerById(Id)
    }
}