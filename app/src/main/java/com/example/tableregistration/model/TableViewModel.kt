package com.example.tableregistration.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.tableregistration.constant.LOG_INIT_TABLE_VIEW_MODEL
import com.example.tableregistration.data.Customer
import com.example.tableregistration.repository.TableRepository
import com.example.tableregistration.data.TableWithCustomers
import com.example.tableregistration.repository.CustomerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TableViewModel(app: Application) : AndroidViewModel(app) {

    val tables = MutableLiveData<List<TableWithCustomers>>()
    val selectedTable = MutableLiveData<TableWithCustomers>()

    private val tableRepository = TableRepository(app)
    private val customerRepository = CustomerRepository(app)

    init {
        Log.i(LOG_INIT_TABLE_VIEW_MODEL, "init: $this")
        initTableWithCustomers()
    }

    fun initTableWithCustomers() {
        CoroutineScope(Dispatchers.IO).launch {
            val tablesWithCustomers = tableRepository.findAllTablesWithCustomer()
            tables.postValue(tablesWithCustomers)
        }
    }

    fun setSelectedTable(tableId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val tableWithCustomers = tableRepository.findTableWithCustomerById(tableId)
            selectedTable.postValue(tableWithCustomers)
        }
    }

    fun saveCustomer(customerName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val tableId = selectedTable.value?.table?.tableId!!
            customerRepository.saveCustomer(Customer(0, customerName, tableId))
            initTableWithCustomers()
        }
    }

    fun clearTables() {
        CoroutineScope(Dispatchers.IO).launch {
            customerRepository.deleteAllCustomer()
            initTableWithCustomers()
        }
    }

    fun clearTable() {
        CoroutineScope(Dispatchers.IO).launch {
            selectedTable.value?.customers?.forEach {
                customerRepository.deleteCustomerById(it.customerId)
            }
            initTableWithCustomers()
        }
    }

    fun isTableFull(): Boolean {
        return selectedTable.value?.customers?.size!! >= 5
    }
}