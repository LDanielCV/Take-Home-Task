package com.assigment.taskcurrencyapp.domain.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class ApiBaseResponse {
    @Expose
    @SerializedName("success")
    var succes:Boolean = false
    @SerializedName("error")
    @Expose
    var error: ErrorResponse? = null
}