package com.assigment.taskcurrencyapp.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rate")
data class Rate(@PrimaryKey(autoGenerate = true) val id:Int, val currency:String, var value:Float, val base:String)
    :BaseModel(currency, value.toString())
