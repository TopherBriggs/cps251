package com.example.contactsproject

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {

    @Insert
    fun insertProduct(product: Product)

//    @Query("SELECT * FROM contacts WHERE contactName = :name")
    @Query("SELECT * FROM contacts WHERE contactName LIKE '%' || :name || '%'")
    fun findProduct(name: String): List<Product>

    @Query("DELETE FROM contacts WHERE contactName = :name")
    fun deleteProduct(name: String)

    @Query("SELECT * FROM contacts")
    fun getAllProducts(): LiveData<List<Product>>
}