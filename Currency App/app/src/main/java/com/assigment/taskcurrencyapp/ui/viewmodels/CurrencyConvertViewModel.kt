package com.assigment.taskcurrencyapp.ui.viewmodels

import com.assigment.taskcurrencyapp.domain.models.Rate
import com.assigment.taskcurrencyapp.domain.repositories.currencyconverter.CurrencyConverterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CurrencyConvertViewModel @Inject constructor(private val currencyConverterRepository: CurrencyConverterRepository) :
    BaseViewModel() {

    private val _rates = MutableStateFlow(emptyList<Rate>())
    private val DEFAULT_CONVERTION_VALUE = 1F

    val rates: StateFlow<List<Rate>>
        get() = _rates

    fun convertValue(valueToConvert: String, base: String): Flow<List<Rate>> {
        val value =
            if (valueToConvert.isEmpty() || valueToConvert.toFloat() <= 0F) DEFAULT_CONVERTION_VALUE else valueToConvert.toFloat()
        return currencyConverterRepository.convertRates(value, base)
    }

    suspend fun getRates(base: String) =
        currencyConverterRepository.getLatestRates(base).collect { resource ->
            mapResource(_rates, resource)
        }

}