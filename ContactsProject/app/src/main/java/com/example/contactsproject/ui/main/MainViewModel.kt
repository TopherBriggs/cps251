package com.example.contactsproject.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.contactsproject.Contact
import com.example.contactsproject.ContactRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ContactRepository = ContactRepository(application)
    private var allContacts: LiveData<List<Contact>>?
    private val searchResults: MutableLiveData<List<Contact>>

    init {
        allContacts = null
        searchResults = repository.searchResults
        sortASC()
    }

    fun insertProduct(contact: Contact) {
        repository.insertProduct(contact)

    }

    fun findProduct(name: String) {
        repository.findProduct(name)
    }

    fun deleteProduct(id: Int) {
        repository.deleteContact(id)
    }

    fun getSearchResults(): MutableLiveData<List<Contact>> {
        return searchResults
    }

    fun getAllContacts(): LiveData<List<Contact>>? {
        return allContacts
    }
    fun sortASC(){
        allContacts = repository.allContactsAsc
    }
    fun sortDESC(){
        allContacts = repository.allContactsDesc


    }
}