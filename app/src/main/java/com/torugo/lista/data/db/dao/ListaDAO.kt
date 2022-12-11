package com.torugo.lista.data.db.dao

import androidx.room.*
import com.torugo.lista.data.db.ListaEntity

@Dao
interface ListaDAO {

    @Query("SELECT * FROM listas WHERE id = :id")
    fun getList(id: Long): ListaEntity

    @Query("SELECT * from listas")
    suspend fun getAll(): List<ListaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(lista: ListaEntity)

    @Delete
    suspend fun deleteList(lista: ListaEntity)

    @Update
    suspend fun updateList(lista: ListaEntity)

    @Query("DELETE FROM listas")
    suspend fun deleteAll()
}