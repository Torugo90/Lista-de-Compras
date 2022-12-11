package com.torugo.lista.data.repository

import com.torugo.lista.data.model.Lista

interface ListaRepository {

    suspend fun createList(lista: Lista)

    fun getList(id: Long): Lista

    suspend fun deleteLista(lista: Lista)

    suspend fun updateList(lista: Lista)

    suspend fun getAll(): List<Lista>

    suspend fun deleteAll()

}
