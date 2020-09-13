package com.example.tableregistration.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Customer (
    @PrimaryKey(autoGenerate=true)
    val customerId: Int,
    val name: String,
    val customerTableId: Int
) {
    override fun toString(): String {
        return name
    }
}