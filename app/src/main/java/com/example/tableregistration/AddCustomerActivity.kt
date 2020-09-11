package com.example.tableregistration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_add_customer.*

class AddCustomerActivity : AppCompatActivity() {

    private val activityTitle by lazy {
        findViewById<TextView>(R.id.activity_title)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_customer)

        activityTitle.text = getString(R.string.add_customer)
    }
}