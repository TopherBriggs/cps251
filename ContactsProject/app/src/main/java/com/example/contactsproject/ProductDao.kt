package com.example.contactsproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    @Query("DELETE FROM contacts WHERE contactID = :id")
    fun deleteProduct(id: Int)

    @Query("SELECT * FROM contacts ORDER BY contactName ASC")
    fun getAllProducts(): List<Product>?

    @Query("SELECT * FROM contacts ORDER BY contactName DESC")
    fun getAllProductsDesc(): List<Product>?
}