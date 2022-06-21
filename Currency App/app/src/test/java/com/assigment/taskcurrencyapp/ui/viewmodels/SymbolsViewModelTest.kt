package com.assigment.taskcurrencyapp.ui.viewmodels



import com.assigment.taskcurrencyapp.fakes.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.assertj.core.api.Assertions.*
import org.junit.After
import org.junit.Before
import org.junit.Test

class SymbolsViewModelTest {
    private lateinit var SUT:SymbolsViewModel

    private  lateinit var fakeRespository:FakeCurrencyCountryRepo

    private val dispatcher = StandardTestDispatcher()
    private val testScope = CoroutineScope(dispatcher)


    @Before
    fun setUp() {
        fakeRespository = FakeCurrencyCountryRepo()
        Dispatchers.setMain(dispatcher)
        SUT = SymbolsViewModel(fakeRespository)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `filterData WHEN query is Empty full list is returned`(){
        testScope.launch {
            val query = ""
            val result = SUT.filterData(query)

            result.collect {
                `assertThat`(it).isEqualTo(filteredCountries)
            }

        }

    }
    @Test
    fun `filterData WHEN query is not Empty fitltered list is returned`(){
        val query = "MEXICAN"
        testScope.launch {

            val result = SUT.filterData(query).first()

            assertThat(result).isEqualTo(filteredCountries)

        }

    }



    @Test
   fun `currencyCountires is fill when getSymbols executed Succes is Returned`() {

        testScope.launch{
            symbol.succes = true

            SUT.getSymbols()

            SUT.currencyCountries.collect{
               assertThat(it).isNotEmpty
            }
        }
   }



    @Test
    fun `errorMessage is no Empty when getSymbols executed, Error is Returned`() {

        testScope.launch{

            SUT.errorMessage.collect{
                assertThat(it).isNotEmpty
            }
        }
    }
}