package com.assigment.taskcurrencyapp.data.db.daos

import androidx.room.Dao
import androidx.room.Query
import com.assigment.taskcurrencyapp.domain.models.Rate
import kotlinx.coroutines.flow.Flow

@Dao
interface LatestRatesDao:BaseDao<Rate>{

    @Query("SELECT * FROM rate WHERE  base= :base  AND currency != :base")
    fun getRates(base:String): Flow<List<Rate>>

}