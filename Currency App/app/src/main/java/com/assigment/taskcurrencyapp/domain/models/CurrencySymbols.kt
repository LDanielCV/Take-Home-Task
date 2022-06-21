package com.assigment.taskcurrencyapp.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_country")
data class CurrencySymbols(@PrimaryKey(autoGenerate = true) val id:Int, val currencySymbols: String, val name:String):BaseModel(currencySymbols,name)

