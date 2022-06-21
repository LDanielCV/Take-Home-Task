package com.assigment.taskcurrencyapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.assigment.taskcurrencyapp.data.db.daos.CurrencyCountryDao
import com.assigment.taskcurrencyapp.data.db.daos.LatestRatesDao
import com.assigment.taskcurrencyapp.domain.models.*


@Database(entities = [CurrencySymbols::class, Rate::class], version = DataBaseInstance.DATA_BASE_VERSION)
abstract class DataBaseInstance: RoomDatabase() {

    abstract val latestRatesDao: LatestRatesDao
    abstract val currencyCountries:CurrencyCountryDao

    companion object {
        const val DATA_BASE_VERSION = 15
        const val DATA_BASE_NAME = "currency_db"
    }


}