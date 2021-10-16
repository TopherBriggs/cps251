package com.example.addnamesavedata.ui.main

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private var names = "";

    fun addName(name : String) {
        if(name.isNotBlank()) {
            this.names += "\n" + name
        }
    }

    fun getName() : String {
        if(names.isBlank()) {
            return "No names to display"
        }
        return names;
    }

}