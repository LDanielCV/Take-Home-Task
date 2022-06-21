package com.assigment.taskcurrencyapp.domain.models


import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LatestRates(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @Expose
    val base: String,
    @Expose
    val date: String,
    @SerializedName("rates")
    @Expose
    val ratesMap: Map<String,Float>,
    @Expose
    val timestamp: Int
): ApiBaseResponse()
