package com.example.contactsproject

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class ContactRepository(application: Application) {

    val searchResults = MutableLiveData<List<Contact>>()
    private var contactDao: ContactDao?
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val allContactsAsc: LiveData<List<Contact>>?
    val allContactsDesc: LiveData<List<Contact>>?


    init {
        val db: ContactRoomDatabase? =
            ContactRoomDatabase.getDatabase(application)
        contactDao = db?.contactDao()
        allContactsAsc = contactDao?.getAllContactsAsc()
        allContactsDesc = contactDao?.getAllContactsDesc()

    }

    fun insertProduct(newContact: Contact) {
        coroutineScope.launch(Dispatchers.IO) {
            asyncInsert(newContact)
        }
    }

    private fun asyncInsert(contact: Contact) {
        contactDao?.insertContact(contact)
        contactDao?.getAllContactsAsc()
        contactDao?.getAllContactsDesc()
    }

    fun deleteContact(id : Int) {
        coroutineScope.launch(Dispatchers.IO) {
            asyncDelete(id)
        }
    }

    private fun asyncDelete(id: Int) {
        contactDao?.deleteContact(id)
    }

    fun findProduct(name: String) {

        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(name).await()
        }
    }

    private fun asyncFind(name: String): Deferred<List<Contact>?> =

        coroutineScope.async(Dispatchers.IO) {
            return@async contactDao?.findContact(name)
        }



}