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
    val allProducts = MutableLiveData<List<Product>>()


    init {
        val db: ProductRoomDatabase? =
            ProductRoomDatabase.getDatabase(application)
        productDao = db?.productDao()
        getAllDesc()

    }

    fun insertProduct(newproduct: Product) {
        coroutineScope.launch(Dispatchers.IO) {
            asyncInsert(newproduct)
        }
    }

    private suspend fun asyncInsert(product: Product) {
        productDao?.insertProduct(product)
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

    fun getAllDesc(){
        coroutineScope.launch(Dispatchers.Main) {
            allProducts.value = asyncAllProducts(true).await()
        }

    }
    fun getAllAsc(){
        coroutineScope.launch(Dispatchers.Main) {
            allProducts.value = asyncAllProducts(false).await()
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

    private suspend fun asyncAllProducts(descending : Boolean?): Deferred<List<Product>?> =

        coroutineScope.async(Dispatchers.IO) {
            if(descending == true){
                return@async productDao?.getAllProductsDesc()

            }
            else {
                return@async productDao?.getAllProducts()
            }
        }

}