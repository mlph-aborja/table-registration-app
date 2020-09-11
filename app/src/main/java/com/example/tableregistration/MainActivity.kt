package com.example.tableregistration

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val tables by lazy {
        arrayOf<Button>(table1, table2, table3, table4)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set tables onclick listener
        tables.iterator().forEach { table -> table.setOnClickListener(this) }
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
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        val table = when(v!!.id) {
            R.id.table1 -> "Table 1"
            R.id.table2 -> "Table 2"
            R.id.table3 -> "Table 3"
            R.id.table4 -> "Table 4"
            else -> ""
        }

        Log.i(Tag.SELECTED_TABLE.name, "onClick: $table")

        val intent = Intent(this, CustomerListActivity::class.java).apply {
            putExtra("table", table)
        }
        startActivity(intent)
    }


}