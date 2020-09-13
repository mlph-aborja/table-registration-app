package com.example.tableregistration.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tableregistration.data.Customer

@Dao
interface CustomerDao {
    @Insert
    fun save(customer: Customer)

    @Query("SELECT * FROM `customer`")
    fun findAll(): List<Customer>

    @Query("SELECT * FROM `customer` WHERE customerId = :customerId")
    fun findById(customerId: Int): Customer

}