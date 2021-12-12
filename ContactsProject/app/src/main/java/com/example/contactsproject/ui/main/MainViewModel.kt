package com.example.contactsproject.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.contactsproject.Product
import com.example.contactsproject.ProductRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductRepository = ProductRepository(application)
    private var allProducts: LiveData<List<Product>>?
    private val searchResults: MutableLiveData<List<Product>>

    init {
        allProducts = repository.allProductsAsc
        searchResults = repository.searchResults
    }

    fun insertProduct(product: Product) {
        repository.insertProduct(product)

    }

    fun findProduct(name: String) {
        repository.findProduct(name)
    }

    fun deleteProduct(id: Int) {
        repository.deleteProduct(id)
    }

    fun getSearchResults(): MutableLiveData<List<Product>> {
        return searchResults
    }

    fun getAllProducts(): LiveData<List<Product>>? {
        return allProducts
    }
    fun sortASC(){
        allProducts = repository.allProductsAsc
    }
    fun sortDESC(){
        allProducts = repository.allProductsDesc

    }
}