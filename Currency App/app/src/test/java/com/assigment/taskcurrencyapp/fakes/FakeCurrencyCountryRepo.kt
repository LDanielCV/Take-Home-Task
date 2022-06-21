package com.assigment.taskcurrencyapp.fakes

import com.assigment.taskcurrencyapp.domain.models.CurrencySymbols
import com.assigment.taskcurrencyapp.domain.repositories.symbols.CurrencyCountryRepository
import com.assigment.taskcurrencyapp.data.server.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeCurrencyCountryRepo: CurrencyCountryRepository {

    override fun getSymbols(): Flow<Resource<out Any?>> = flowOf(
        try{
            when(getSymbol("symbols").succes) {
                true ->
                    Resource.Success(countries)

                else ->
                    Resource.Error(null, "Failure")
            }
        }catch (ex:Exception){
            Resource.Error(null,"NetWorkException")
        }
    )

    override fun searchCountries(query: String): Flow<List<CurrencySymbols>> = flowOf(countries.filter {
        it.name.contains(query)
    })

    override suspend fun saveListData(list: List<CurrencySymbols>) {
        TODO("Not yet implemented")
    }


}