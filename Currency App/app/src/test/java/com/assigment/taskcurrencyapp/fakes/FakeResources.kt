package com.assigment.taskcurrencyapp.fakes

import com.assigment.taskcurrencyapp.domain.models.*

val rates = mutableListOf(
    Rate(0,"EUR", 1F, "EUR"),
    Rate(1,"MXN", 25.3F, "EUR"),
    Rate(2,"USD", 1.3F, "EUR")
)

val countries = mutableListOf(
    CurrencySymbols(0,"EUR", "EURO"),
    CurrencySymbols(1,"MXN", "MEXICAN PESO"),
    CurrencySymbols(2,"EUR", "EURO"),
)
val filteredCountries = mutableListOf(
    CurrencySymbols(1,"MXN", "MEXICAN PESO")
)




val apiError = ApiBaseResponse()

val latestRates = LatestRates(0,"EUR","24-06-2021",
    mapOf("EUR" to 1F,"USD" to 1.1F, "MXN" to 25.3F),1)

val symbol = Symbols(mapOf("MXN" to "Mexican Peso",
    "EUR" to "Euro",
    "USD" to "United States Dolar"))

val ratesMapping = mapOf("EUR" to latestRates)

val symbolMaping = mapOf("symbols" to symbol)

fun getRates(value:Float = 1F) =
    rates.map {
        it.value = it.value * value
        it
    }

fun getSymbol(key:String = ""):ApiBaseResponse {
    val result = symbolMaping[key]
    return result?: apiError
}

fun searchMap(base:String):ApiBaseResponse {
    val result = ratesMapping[base]

    return result?:apiError
}


