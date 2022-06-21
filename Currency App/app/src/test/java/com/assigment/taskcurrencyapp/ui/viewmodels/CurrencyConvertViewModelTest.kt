package com.assigment.taskcurrencyapp.ui.viewmodels

import com.assigment.taskcurrencyapp.fakes.FakeCurrencyRepo
import com.assigment.taskcurrencyapp.fakes.getRates
import com.assigment.taskcurrencyapp.fakes.rates
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import java.util.*

class CurrencyConvertViewModelTest {

    private lateinit var SUT:CurrencyConvertViewModel

    private val fakeRespository = FakeCurrencyRepo()

    private val dispatcher = StandardTestDispatcher()
    private val testScope = CoroutineScope(dispatcher)


    @Before
    fun setUp() {
        SUT = CurrencyConvertViewModel(fakeRespository)
    }


    @Test
    fun `convertValue WHEN valuteToConvert 0 THEN use defalut value for converting`() = runBlockingTest {
        //GIVEN
        val valueToConvert = ""
        val base = "EUR"
        //WHEN
        val result = SUT.convertValue(valueToConvert,base)
        //THEN
        result.collect {
            `assertEquals`(rates, it)
        }
    }


    @Test
    fun `convertValue WHEN valueToConvert isEmpty, use default for converting`() = runBlockingTest{
        //GIVEN
        val valueToConvert = ""
        val base = "EUR"
        //WHEN
        val result = SUT.convertValue(valueToConvert,base)
        //THEN
        result.collect {
            `assertEquals`(rates, it)
        }
    }

    @Test
    fun `convertValue WHEN valueToConvert grater than 0, return converted list`() = runBlockingTest{
        //GIVEN
        val valueToConvert = "19"
        val base = "EUR"
        //WHEN
        val result = SUT.convertValue(valueToConvert,base)

        val expectedResult = getRates(valueToConvert.toFloat())
        //THEN
        result.collect {
            assertEquals(expectedResult, it)
        }
    }


    @Test
    fun `getRates WHEN base is diferent than EUR, errorMessage is recieved`() {
        //GIVEN
        val base = "MXN"
        //WHEN
        testScope.launch {
            SUT.getRates(base)

            SUT.errorMessage.collect {
                `assert`(it.isNotEmpty())
            }
        }
    }

    @Test
    fun `getRates WHEN base is EUR, rates list is recieved`() {
        //GIVEN
        val base = "EUR"
        //WHEN
        testScope.launch {
            SUT.getRates(base)

            SUT.rates.collect {
                `assert`(it.isNotEmpty())
            }
        }
    }

}