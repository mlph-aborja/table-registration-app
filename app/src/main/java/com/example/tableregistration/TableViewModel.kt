package com.example.tableregistration

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class TableViewModel(app: Application) : AndroidViewModel(app) {

    val tables = MutableLiveData<List<Table>>()

    init {
        Log.i(LOG_INIT_TABLE_VIEW_MODEL, "init: $this")
        tables.value = arrayListOf(
            Table(1, app.getString(R.string.table_1), ArrayList()),
            Table(2, app.getString(R.string.table_2), ArrayList()),
            Table(3, app.getString(R.string.table_3), ArrayList()),
            Table(4, app.getString(R.string.table_4), ArrayList())
        )
    }
}