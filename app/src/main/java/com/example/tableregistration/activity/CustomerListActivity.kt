package com.example.tableregistration.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.tableregistration.R
import com.example.tableregistration.constant.LOG_SELECTED_TABLE
import com.example.tableregistration.model.TableViewModel

class CustomerListActivity : AppCompatActivity() {

    private lateinit var tableViewModel: TableViewModel

    private val activityTitle by lazy {
        findViewById<TextView>(R.id.activity_title)
    }

    private val customerList by lazy {
        findViewById<ListView>(R.id.customer_list)
    }

    private val clearTable by lazy {
        findViewById<Button>(R.id.clear_table)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_list)

        tableViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
            .get(TableViewModel::class.java)

        tableViewModel.selectedTable.observe(this, {
            Log.i(LOG_SELECTED_TABLE, "onCreate: ${it.table.name}")
            activityTitle.text = it.table.name

            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, it.customers)
            customerList.adapter = adapter
        })

        clearTable.setOnClickListener{
            tableViewModel.clearTable()
            Toast.makeText(this, "Table has been cleared", Toast.LENGTH_LONG).show()
            finish()
        }

        val tableId = intent.getIntExtra("tableId", 0)
        tableViewModel.setSelectedTable(tableId)
    }
}