package com.torugo.lista.data.repository

import com.torugo.lista.data.db.dao.ItensDAO
import com.torugo.lista.data.db.toItem
import com.torugo.lista.data.db.toItensEntity
import com.torugo.lista.data.db.toListItens
import com.torugo.lista.data.model.Item

class ItemDbDataSource(
    private val itensDAO: ItensDAO
): ItemRepository {

    override suspend fun getAll(idLista: Long): List<Item> {
        return itensDAO.getAll(idLista).toListItens()
    }

    override suspend fun addItem(item: Item) {
        return itensDAO.save(item.toItensEntity())
    }

    override fun getItem(id: Long): Item {
        return itensDAO.getItem(id).toItem()
    }

    override suspend fun deleteItem(item: Item) {
        return itensDAO.deleteItem(item.toItensEntity())
    }

    override suspend fun updateItem(item: Item) {
        return itensDAO.updateItem(item.toItensEntity())
    }

    override suspend fun deleteAll(idLista: Long) {
        return itensDAO.deleteAll(idLista)
    }

    override suspend fun total(idLista: Long): Double {
        return itensDAO.total(idLista)
    }
}