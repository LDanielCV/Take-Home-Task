package com.assigment.taskcurrencyapp.di.data.db

import android.app.Application
import androidx.room.Room
import com.assigment.taskcurrencyapp.data.db.DataBaseInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideDataBaseInstance(app: Application) = Room.databaseBuilder(
        app, DataBaseInstance::class.java, DataBaseInstance.DATA_BASE_NAME
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideLatestRatesDao(dataBaseInstance: DataBaseInstance) =
        dataBaseInstance.latestRatesDao

    @Provides
    fun provideCurrencyCountryDao(dataBaseInstance: DataBaseInstance) =
        dataBaseInstance.currencyCountries
}