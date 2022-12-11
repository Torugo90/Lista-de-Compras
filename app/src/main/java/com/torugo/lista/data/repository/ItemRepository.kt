package com.torugo.lista.data.repository

import com.torugo.lista.data.model.Item

interface ItemRepository {

    suspend fun addItem(item: Item)

    fun getItem(id: Long): Item

    suspend fun deleteItem(item: Item)

    suspend fun updateItem(item: Item)

    suspend fun getAll(idLista: Long): List<Item>

    suspend fun deleteAll(idLista: Long)
}