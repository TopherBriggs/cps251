package com.example.contactsproject

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class ProductRepository(application: Application) {

    val searchResults = MutableLiveData<List<Product>>()
    private var productDao: ProductDao?
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val allProductsAsc: LiveData<List<Product>>?
    val allProductsDesc: LiveData<List<Product>>?


    init {
        val db: ProductRoomDatabase? =
            ProductRoomDatabase.getDatabase(application)
        productDao = db?.productDao()
        allProductsAsc = productDao?.getAllProducts()
        allProductsDesc = productDao?.getAllProductsDesc()

    }

    fun insertProduct(newproduct: Product) {
        coroutineScope.launch(Dispatchers.IO) {
            asyncInsert(newproduct)
        }
    }

    private suspend fun asyncInsert(product: Product) {
        productDao?.insertProduct(product)
        productDao?.getAllProducts()
        productDao?.getAllProductsDesc()
    }

    fun deleteProduct(id : Int) {
        coroutineScope.launch(Dispatchers.IO) {
            asyncDelete(id)
        }
    }

    private suspend fun asyncDelete(id: Int) {
        productDao?.deleteProduct(id)
    }

    fun findProduct(name: String) {

        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(name).await()
        }
    }

    /*fun getAllProductsDesc(){
        coroutineScope.launch { Dispatchers.Main{
            allProducts = asyncAllProducts(true).await()!!
        } }

    }*/

    private suspend fun asyncFind(name: String): Deferred<List<Product>?> =

        coroutineScope.async(Dispatchers.IO) {
            return@async productDao?.findProduct(name)
        }



}