package com.torugo.lista.data.db.dao

import androidx.room.*
import com.torugo.lista.data.db.ItensEntity

@Dao
interface ItensDAO {

    @Query("SELECT * FROM itens WHERE id = :id")
    fun getItem(id: Long): ItensEntity

    @Query("SELECT * from itens WHERE idLista = :idLista")
    suspend fun getAll(idLista: Long): List<ItensEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(item: ItensEntity)

    @Delete
    suspend fun deleteItem(item: ItensEntity)

    @Update
    suspend fun updateItem(item: ItensEntity)

    @Query("DELETE FROM itens WHERE idLista = :idLista")
    suspend fun deleteAll(idLista: Long)

    @Query("SELECT SUM(valor) FROM itens where idLista = :idLista")
    suspend fun total(idLista: Long): Double
}