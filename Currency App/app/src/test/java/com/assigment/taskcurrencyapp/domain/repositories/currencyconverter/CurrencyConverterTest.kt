package com.assigment.taskcurrencyapp.domain.repositories.currencyconverter

import com.assigment.taskcurrencyapp.data.db.daos.LatestRatesDao
import com.assigment.taskcurrencyapp.data.server.common.Resource
import com.assigment.taskcurrencyapp.data.server.services.CurrencyService
import com.assigment.taskcurrencyapp.fakes.getRates
import com.assigment.taskcurrencyapp.fakes.latestRates
import com.assigment.taskcurrencyapp.fakes.rates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CurrencyConverterTest{


    private lateinit var SUT: CurrencyConverterImpl

    @Mock
    private lateinit var mockCurrencyService:CurrencyService

    @Mock
    private lateinit var latestRatesDao: LatestRatesDao

    private val dispatcher = StandardTestDispatcher()
    private val testScope = CoroutineScope(dispatcher)

    private val EUR = "EUR"

    @Before
    fun setUp() {
       SUT = CurrencyConverterImpl(mockCurrencyService,latestRatesDao)

    }


    @Test
    fun `getLatestRates WHEN base is not EUR, Resource Error is recieved`(){
        val base = "MXN"
        testScope.launch {

            setUpError()


            val result = SUT.getLatestRates(base)

            result.collect {
                `assertEquals`(Resource.Error("", ""), it)
            }
        }

    }


    @Test
    fun `getLatestRates WHEN base is EUR, Resource Succes is recieved`(){
        val base = "EUR"
        testScope.launch {

            setUpSucces()
            val result = SUT.getLatestRates(base)

            result.collect {
                `assertEquals`( Resource.Success(""), it)
            }
        }
    }


    @Test
    fun `convertValue ,rates list with value field multiplied is Received`(){
        val valueToConvert = 3F

        testScope.launch {
            setUpDataBaseInfo()

            val result = SUT.convertRates(valueToConvert,EUR)

            val expected = getRates()
            result.collect {
                `assertEquals`(expected, it)
            }
        }

    }

    private suspend fun setUpDataBaseInfo(){

        val flow = flow {
            emit(rates)
        }
        `when`(latestRatesDao.getRates(anyString())).thenReturn(flow)
    }

    private suspend fun setUpError(){
        `when`(
            mockCurrencyService.getLatest(anyString(), anyString()))
            .thenReturn(latestRates)
    }

    private suspend fun setUpSucces(){
        latestRates.succes = true
        `when`(
            mockCurrencyService.getLatest(anyString(), anyString()))
            .thenReturn(latestRates)
    }


}