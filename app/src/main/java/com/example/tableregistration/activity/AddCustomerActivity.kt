package com.example.tableregistration.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.tableregistration.R
import com.example.tableregistration.model.TableViewModel

class AddCustomerActivity : AppCompatActivity() {

    private lateinit var tableViewModel: TableViewModel

    private val activityTitle by lazy {
        findViewById<TextView>(R.id.activity_title)
    }

    private val tableSpinner by lazy {
        findViewById<Spinner>(R.id.tables_spinner)
    }

    private val addCustomer by lazy {
        findViewById<Button>(R.id.add_customer)
    }

    private val customerName by lazy {
        findViewById<EditText>(R.id.customer_name)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_customer)

        tableViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
            .get(TableViewModel::class.java)

        tableViewModel.tables.observe(this, {
            val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, it)
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

            tableSpinner.adapter = adapter
        })

        addCustomer.setOnClickListener {
            val customerName = customerName.text.toString()
            val tableId: Int = 1 + tableSpinner.selectedItemId.toInt()

            tableViewModel.saveCustomer(customerName = customerName, tableId = tableId)
            finish()
        }

        activityTitle.text = getString(R.string.add_customer)
    }
}