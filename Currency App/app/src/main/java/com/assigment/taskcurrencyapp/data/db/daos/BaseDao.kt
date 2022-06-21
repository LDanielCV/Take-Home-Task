package com.assigment.taskcurrencyapp.data.db.daos

import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDao<DBO> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(data:List<DBO>):List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data:DBO):Long
}