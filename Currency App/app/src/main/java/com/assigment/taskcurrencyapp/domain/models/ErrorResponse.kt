package com.assigment.taskcurrencyapp.domain.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("code") @Expose
    val code:Int,
    @SerializedName("type") @Expose
    val type:String)
