package com.assigment.taskcurrencyapp.domain.repositories.currencyconverter

import com.assigment.taskcurrencyapp.data.db.daos.LatestRatesDao
import com.assigment.taskcurrencyapp.domain.models.Rate
import com.assigment.taskcurrencyapp.domain.repositories.BaseRepository
import com.assigment.taskcurrencyapp.data.server.services.CurrencyService
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrencyConverterImpl @Inject constructor(private val latestRatesService: CurrencyService, private val latestRatesDao: LatestRatesDao):
    BaseRepository(), CurrencyConverterRepository {

    override fun getLatestRates(base:String) = mapNetWorkResource(
            query = {
            latestRatesDao.getRates(base)
                    },
            fetchNetWorkResource = {
                callService { latestRatesService.getLatest(base = base)}
            },saveFetchedResult = {
            val result = it.ratesMap.toList()
            saveDataList(result.map { it.toRate(base)})
        },shouldFetch = {
            it.isEmpty()
        })

   private fun Pair<String,Float>.toRate(base:String): Rate =
        Rate(0,first, second, base)

    override fun convertRates(valueToConvert: Float, base: String) =
        latestRatesDao.getRates(base).map {
            it.map { rate ->
                rate.value *= valueToConvert
                rate
            }
        }

    override suspend fun saveDataList(list: List<Rate>) {
        latestRatesDao.insertList(list)
    }
}