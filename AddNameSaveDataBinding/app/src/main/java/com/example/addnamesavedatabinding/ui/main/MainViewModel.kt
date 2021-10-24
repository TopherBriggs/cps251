package com.example.addnamesavedatabinding.ui.main

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var names: MutableLiveData<String> = MutableLiveData("No Names to Display")
    var empty = true;


    fun addName(name : Editable) {
        names.let {
            if (name.isNotBlank()) {
                if (empty) {        //gets rid of 'No Names to Display' when first name is added
                    names.value = "";
                    empty = false;
                }
                names.value +=  "\n" + name
            }
        }
    }
    /*
    fun getName() : String {
        if(names.isBlank()) {
            return "No names to display"
        }
        return names;
    }
    */

}