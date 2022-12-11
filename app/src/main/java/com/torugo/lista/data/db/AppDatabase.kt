package com.torugo.lista.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.torugo.lista.data.db.dao.ItensDAO
import com.torugo.lista.data.db.dao.ListaDAO

@Database(entities = [ListaEntity::class, ItensEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun listaDao(): ListaDAO
    abstract fun itensDao(): ItensDAO

    companion object{
        @Volatile
        private  var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration()
                 .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}