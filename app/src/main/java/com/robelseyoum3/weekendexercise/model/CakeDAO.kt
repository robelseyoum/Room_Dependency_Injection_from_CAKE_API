package com.robelseyoum3.weekendexercise.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface CakeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCake(cakes: List<CakeResult>): Completable

    @Query("Select * from cake_table")
    fun getAllCakes(): Flowable<List<CakeResult>>


}
