package com.example.tableregistration.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.tableregistration.constant.LOG_OBSERVE_TABLE_CHANGE
import com.example.tableregistration.constant.LOG_SELECTED_TABLE
import com.example.tableregistration.R
import com.example.tableregistration.model.TableViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener, LifecycleOwner {

    private lateinit var tableViewModel: TableViewModel

    private val tables by lazy {
        arrayOf<Button>(table1, table2, table3, table4)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tableViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
            .get(TableViewModel::class.java)

        // Set tables
        tableViewModel.tables.observe(this, {
            Log.i(LOG_OBSERVE_TABLE_CHANGE, "onCreate: TableViewModel Change")
            tables.forEachIndexed { index, table ->
                val buttonText = "${it[index].table.name}\nCustomers: ${it[index].customers.size}"
                table.text = buttonText
                table.setOnClickListener(this)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        tableViewModel.initTableWithCustomers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add_customer) {
            val intent = Intent(this, AddCustomerActivity::class.java)
            startActivity(intent)
            return true
        } else if (item.itemId == R.id.menu_clear_tables) {
            tableViewModel.clearTables()
            Toast.makeText(this, "All tables has been cleared", Toast.LENGTH_LONG).show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        val tableId = when(v!!.id) {
            R.id.table1 -> 1
            R.id.table2 -> 2
            R.id.table3 -> 3
            R.id.table4 -> 4
            else -> 0
        }

        Log.i(LOG_SELECTED_TABLE, "onClick: $tableId")

        val intent = Intent(this, CustomerListActivity::class.java).apply {
            putExtra("tableId", tableId)
        }

        startActivity(intent)

    }


}