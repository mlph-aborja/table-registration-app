package com.example.tableregistration.activity

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
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

        tableViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory(
                application
            )
        ).get(TableViewModel::class.java)

        tableViewModel.tables.observe(this, {
            val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, it)
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

            tableSpinner.adapter = adapter
        })

        tableSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val tableId: Int = 1 + position
                tableViewModel.setSelectedTable(tableId)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                val defaultTable = 1
                tableViewModel.setSelectedTable(defaultTable)
            }
        }

        addCustomer.setOnClickListener {
            val customerName = customerName.text.toString()

            if (tableViewModel.isTableFull()) {
                AlertDialog.Builder(this)
                    .setTitle("Table is fully occupied")
                    .setMessage("Five customers are allowed per table")
                    .setCancelable(false)
                    .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }.show()
            } else {
                tableViewModel.saveCustomer(customerName = customerName)
                Toast.makeText(this, "$customerName has been added",Toast.LENGTH_LONG).show()
                finish()
            }
        }

        activityTitle.text = getString(R.string.add_customer)
    }
}