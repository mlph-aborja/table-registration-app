package com.example.tableregistration.repository

import android.app.Application
import android.util.Log
import com.example.tableregistration.constant.LOG_INIT_TABLE
import com.example.tableregistration.data.Customer
import com.example.tableregistration.data.Table
import com.example.tableregistration.database.TableDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomerRepository(val app: Application) {

    private val customerDao = TableDatabase.getDatabase(app)!!.getCustomerDao()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            Log.i(LOG_INIT_TABLE, "init: Customer")
            customerDao.findAll().forEach { Log.i(LOG_INIT_TABLE, "init: $it") }
        }
    }

    fun saveCustomer(customer: Customer) {
        customerDao.save(customer)
    }
}