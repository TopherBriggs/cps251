package com.example.recyclerviewproject

import androidx.lifecycle.ViewModel
import kotlin.random.Random

class MainViewModel : ViewModel() {
    private val titles = List(8) { Random.nextInt(0, 8) }
    private val details = List(8) { Random.nextInt(0, 8) }
    private val images = List(8) { Random.nextInt(0, 8) }


    fun getTitles() : List<Int>{
        return titles;
    }
    fun getDetails() : List<Int>{
        return details;
    }
    fun getImages() : List<Int>{
        return images;
    }
}