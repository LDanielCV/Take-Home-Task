package com.assigment.taskcurrencyapp.domain.repositories.symbols

import com.assigment.taskcurrencyapp.data.db.daos.CurrencyCountryDao
import com.assigment.taskcurrencyapp.data.server.common.Resource
import com.assigment.taskcurrencyapp.data.server.services.CurrencyService
import com.assigment.taskcurrencyapp.fakes.countries
import com.assigment.taskcurrencyapp.fakes.filteredCountries
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CurrencyCountryTest {

    @Mock
    private lateinit var serviceMock: CurrencyService

    @Mock
    private lateinit var symbosMockDao:CurrencyCountryDao

    private lateinit var SUT:CurrencyCountryImpl

    private val dispatcher = StandardTestDispatcher()
    private val testScope = CoroutineScope(dispatcher)


    @Before
    fun setUp() {
        SUT = CurrencyCountryImpl(serviceMock,symbosMockDao)
    }

    @Test
    fun `getLatestRates WHEN base is not EUR, Resource Error is recieved`(){
        testScope.launch {

            val result = SUT.getSymbols()

            result.collect {

                `assertThat`(it).isEqualToComparingFieldByField(Resource.Error(null,"Failure"))

            }
        }

    }


    @Test
    fun `getLatestRates WHEN base is EUR, Resource Succes is recieved`(){
        testScope.launch {

            val result = SUT.getSymbols()

            result.collect {
                assertThat(it).isEqualToComparingFieldByField(Resource.Success(countries))
            }
        }
    }


    @Test
    fun `searchCountries return filteredList`(){
        testScope.launch {
            setUpDataBaseInfo()
            val query = "MEXICAN"
            val result = SUT.searchCountries(query)

            result.collect {
                assertThat(it).isEqualTo(filteredCountries)
            }

        }

    }
    private suspend fun setUpDataBaseInfo(){

        val flow = flow {
            emit(filteredCountries)
        }

        Mockito.`when`(symbosMockDao.getSearchedCountries(Mockito.anyString())).thenReturn(flow)
    }

}