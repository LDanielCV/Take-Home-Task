package com.assigment.taskcurrencyapp.domain.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Symbols(
    @SerializedName("symbols")
    @Expose
    val symbols: Map<String,String>
):ApiBaseResponse()