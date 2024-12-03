package com.example.asuapp001.ui.diaryPrilagusha

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class diaryPrilagushaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }

    val text: LiveData<String> = _text
}