package com.android.rsvpform.data.repository

import com.android.rsvpform.data.api.ApiService
import com.android.rsvpform.data.model.response.ErrorResponse
import com.android.rsvpform.utils.NetworkState
import com.google.gson.Gson

class MainRepository constructor(private val retrofitService: ApiService) {

    suspend fun submitForm(
        firstName: String,
        lastName: String,
        contactNumber: String,
        email: String
    ): NetworkState<String> {
        val response = retrofitService.submitForm(
            firstName = firstName,
            lastName = lastName,
            contactNo = contactNumber,
            email = email
        )

        return if (response.isSuccessful) {
            return NetworkState.Success(response.body()?.string() ?: "")
        } else {
            NetworkState.Error(
                Gson().fromJson(
                    response.errorBody()?.string() ?: "{}",
                    ErrorResponse::class.java
                ).title
            )
        }
    }
}
