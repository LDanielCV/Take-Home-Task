package com.assigment.taskcurrencyapp.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.assigment.taskcurrencyapp.domain.models.CurrencySymbols
import com.assigment.taskcurrencyapp.domain.repositories.symbols.CurrencyCountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SymbolsViewModel @Inject constructor(private val currencyCountryRepository: CurrencyCountryRepository) :
    BaseViewModel() {

    private val _currencyCountries = MutableStateFlow(emptyList<CurrencySymbols>())

    val currencyCountries: StateFlow<List<CurrencySymbols>>
        get() = _currencyCountries


    fun filterData(text: String) = currencyCountryRepository.searchCountries(text)

    init {
        viewModelScope.launch {
            getSymbols()
        }
    }

    suspend fun getSymbols() = currencyCountryRepository.getSymbols().collect { resource ->
        mapResource(_currencyCountries, resource)
    }
}