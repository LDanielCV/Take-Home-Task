package com.assigment.taskcurrencyapp.di.domain

import com.assigment.taskcurrencyapp.data.db.daos.CurrencyCountryDao
import com.assigment.taskcurrencyapp.data.db.daos.LatestRatesDao
import com.assigment.taskcurrencyapp.domain.repositories.currencyconverter.CurrencyConverterRepository
import com.assigment.taskcurrencyapp.domain.repositories.currencyconverter.CurrencyConverterImpl
import com.assigment.taskcurrencyapp.domain.repositories.symbols.CurrencyCountryImpl
import com.assigment.taskcurrencyapp.domain.repositories.symbols.CurrencyCountryRepository
import com.assigment.taskcurrencyapp.data.server.services.CurrencyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {
    @Provides
    @ViewModelScoped
    fun providesCurrencyCountryRepository(
        currencyService: CurrencyService,
        currencyCountryDao: CurrencyCountryDao
    ): CurrencyCountryRepository =
        CurrencyCountryImpl(currencyService, currencyCountryDao)

    @Provides
    @ViewModelScoped
    fun providesCurrencyRepository(
        currencyService: CurrencyService,
        latestRatesDao: LatestRatesDao
    ): CurrencyConverterRepository =
        CurrencyConverterImpl(currencyService, latestRatesDao)

}