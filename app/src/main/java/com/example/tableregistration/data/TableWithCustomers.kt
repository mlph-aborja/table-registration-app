package com.example.tableregistration.data

import androidx.room.Embedded
import androidx.room.Relation

data class TableWithCustomers (
    @Embedded val table: Table,
    @Relation(
        parentColumn = "tableId",
        entityColumn = "customerTableId"
    )
    val customers: List<Customer>
) {
    override fun toString(): String {
        return table.name
    }
}