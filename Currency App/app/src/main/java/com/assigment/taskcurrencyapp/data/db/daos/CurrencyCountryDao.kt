package com.assigment.taskcurrencyapp.data.db.daos

import androidx.room.Dao
import androidx.room.Query
import com.assigment.taskcurrencyapp.domain.models.CurrencySymbols
import kotlinx.coroutines.flow.Flow


@Dao
interface CurrencyCountryDao:BaseDao<CurrencySymbols> {

    @Query("SELECT * FROM currency_country")
    fun getCurrencyCountries(): Flow<List<CurrencySymbols>>

    @Query("SELECT * FROM currency_country WHERE name LIKE '%' || :searchQuery || '%'")
    fun getSearchedCountries(searchQuery:String):Flow<List<CurrencySymbols>>


}