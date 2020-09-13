package com.example.tableregistration.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Table(
    @PrimaryKey(autoGenerate=true)
    val tableId: Int,
    val name: String,
)