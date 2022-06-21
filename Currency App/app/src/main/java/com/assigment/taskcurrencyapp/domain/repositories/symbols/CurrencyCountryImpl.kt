package com.assigment.taskcurrencyapp.domain.repositories.symbols

import com.assigment.taskcurrencyapp.data.db.daos.CurrencyCountryDao
import com.assigment.taskcurrencyapp.domain.models.CurrencySymbols
import com.assigment.taskcurrencyapp.domain.repositories.BaseRepository
import com.assigment.taskcurrencyapp.data.server.services.CurrencyService
import javax.inject.Inject


class CurrencyCountryImpl @Inject constructor(private val currencyService: CurrencyService, private val countryDao:CurrencyCountryDao):
    BaseRepository(), CurrencyCountryRepository{

   override fun getSymbols() = mapNetWorkResource(query = {
        countryDao.getCurrencyCountries()
    },fetchNetWorkResource = {
        callService { currencyService.getSymbols() }
    }, saveFetchedResult = {
        val result = it.symbols.toList()
        saveListData(result.map { it.toCountry() })
    }, shouldFetch = {
        it.isEmpty()
    })

    private fun Pair<String,String>.toCountry(): CurrencySymbols =
        CurrencySymbols(0,first,second)

    override fun searchCountries(query:String) =
        if(!query.isEmpty())
            countryDao.getSearchedCountries(query)
        else
            countryDao.getCurrencyCountries()

    override suspend fun saveListData(list: List<CurrencySymbols>) {
        countryDao.insertList(list)
    }
}