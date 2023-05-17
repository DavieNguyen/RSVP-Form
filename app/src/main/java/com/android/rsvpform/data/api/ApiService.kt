package com.android.rsvpform.data.api

import com.android.rsvpform.BuildConfig
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("interviewapi/AssessmentTestRSVP/")
    suspend fun submitForm(
        @Query("ApiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("FirstName") firstName: String,
        @Query("LastName") lastName: String,
        @Query("ContactNo") contactNo: String,
        @Query("Email") email: String
    ): Response<ResponseBody>
}
