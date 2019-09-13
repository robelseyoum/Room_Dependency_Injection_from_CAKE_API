package com.robelseyoum3.weekendexercise.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(CakeResult::class),version = 1, exportSchema = false)
abstract class CakeDatabase: RoomDatabase() {

    abstract fun CakeDAO(): CakeDAO

    companion object{

        @Volatile
        private var INSTANCE: CakeDatabase? = null

        fun getDatabase(context: Context): CakeDatabase?{

            val temInstances = INSTANCE
            if(INSTANCE != null)
            {
                return temInstances
            }
            synchronized(this){
                val instances = Room.databaseBuilder(context.applicationContext,
                                CakeDatabase::class.java, "cake_table"
                                ).build()
                INSTANCE = instances

            }
            return INSTANCE
        }
    }
}
