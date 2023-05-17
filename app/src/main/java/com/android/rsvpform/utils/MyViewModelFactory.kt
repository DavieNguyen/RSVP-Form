package com.android.rsvpform.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.rsvpform.data.repository.MainRepository
import com.android.rsvpform.ui.MainViewModel

class MyViewModelFactory constructor(private val repository: MainRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}