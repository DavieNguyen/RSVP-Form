package com.android.rsvpform.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.rsvpform.data.model.Result
import com.android.rsvpform.data.repository.MainRepository
import com.android.rsvpform.utils.NetworkState
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    val loading = MutableLiveData<Boolean>()
    val result = MutableLiveData<Result>()

    fun submitForm(firstName: String, lastName: String, contactNumber: String, email: String) {
        viewModelScope.launch {
            loading.postValue(true)
            when (val response = mainRepository.submitForm(
                firstName = firstName,
                lastName = lastName,
                contactNumber = contactNumber,
                email = email
            )) {
                is NetworkState.Success -> {
                    result.postValue(Result(response.message))
                    loading.postValue(false)
                }

                is NetworkState.Error -> {
                    result.postValue(Result(response.errorMessage, false))
                    loading.postValue(false)
                }
            }
        }
    }
}
