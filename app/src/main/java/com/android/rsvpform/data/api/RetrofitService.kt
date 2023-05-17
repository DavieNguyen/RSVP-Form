package com.android.rsvpform.data.api

import com.android.rsvpform.BuildConfig
import retrofit2.Retrofit

class RetrofitService {
    companion object {
        private var retrofitService: ApiService? = null
        fun getInstance(): ApiService {
            if (retrofitService == null) {
                // Because response didn't return json format, then can't use Gson default converter
//               val gson = GsonBuilder().setLenient().create()
//               val gsonConverter = GsonConverterFactory.create(gson)
                val retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .build()
                retrofitService = retrofit.create(ApiService::class.java)
            }
            return retrofitService!!
        }
    }
}
