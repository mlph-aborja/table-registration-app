package com.example.tableregistration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class CustomerListActivity : AppCompatActivity() {

    private val activityTitle by lazy {
        findViewById<TextView>(R.id.activity_title)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_list)

        activityTitle.text = getString(R.string.customer_list)
    }
}