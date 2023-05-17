package com.android.rsvpform.data.model.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerializedName("type")
    val type: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("traceId")
    val traceId: String
)