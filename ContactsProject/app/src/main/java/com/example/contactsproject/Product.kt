package com.example.contactsproject

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
class Product {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "contactId")
    var id: Int = 0

    @ColumnInfo(name = "contactName")
    var contactName: String? = null
    var phoneNumber: String = ""

    constructor() {}

    constructor(id: Int, productName: String, phoneNumber: String) {
        this.contactName = productName
        this.phoneNumber = phoneNumber
    }
    constructor(productName: String, phoneNumber: String) {
        this.contactName = productName
        this.phoneNumber = phoneNumber
    }
}