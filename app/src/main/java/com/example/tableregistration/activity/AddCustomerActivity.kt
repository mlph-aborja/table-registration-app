package com.example.tableregistration.activity

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tableregistration.R
import com.example.tableregistration.model.TableViewModel


class AddCustomerActivity : AppCompatActivity() {

    private lateinit var tableViewModel: TableViewModel
    private val CHANNEL_ID = "com.example.tableregistration"
    private val NOTIFICATION_ID = 12345

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
                Toast.makeText(this, "$customerName has been added table",Toast.LENGTH_LONG).show()
                createNotificationChannel()
                sendNotification(customerName)
                finish()
            }
        }

        activityTitle.text = getString(R.string.add_customer)
    }

    private fun sendNotification(customerName: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("New Customer")
            .setContentText("$customerName has been added to table")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Table Registration"
            val descriptionText = "Table Registration Notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}