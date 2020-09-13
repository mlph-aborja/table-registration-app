package com.example.tableregistration.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.tableregistration.data.Customer
import com.example.tableregistration.data.Table
import com.example.tableregistration.data.TableWithCustomers

@Dao
interface TableDao {

    @Insert
    fun save(table: Table)

    @Query("SELECT * FROM `table`")
    fun findAll(): List<Table>

    @Query("SELECT * FROM `table` WHERE tableId = :tableId")
    fun findById(tableId: Int): Table

    @Transaction
    @Query("SELECT * FROM `Table`")
    fun findTablesWithCustomers(): List<TableWithCustomers>

    @Query("SELECT * FROM `table` WHERE tableId = :tableId")
    fun findTableWithCustomerById(tableId: Int): TableWithCustomers
}