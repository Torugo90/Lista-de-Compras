package com.torugo.lista.data.repository

import com.torugo.lista.data.db.dao.ListaDAO
import com.torugo.lista.data.db.toListLista
import com.torugo.lista.data.db.toLista
import com.torugo.lista.data.db.toListaEntity
import com.torugo.lista.data.model.Lista

class ListaDbDataSource(
    private val listaDAO: ListaDAO
): ListaRepository {

    override suspend fun getAll(): List<Lista> {
        return listaDAO.getAll().toListLista()
    }

    override suspend fun createList(lista: Lista) {
        listaDAO.save(lista.toListaEntity())
    }

    override fun getList(id: Long): Lista {
        return listaDAO.getList(id).toLista()
    }

    override suspend fun updateList(lista: Lista) {
        return listaDAO.updateList(lista.toListaEntity())
    }

    override suspend fun deleteAll() {
        return listaDAO.deleteAll()
    }

    override suspend fun deleteLista(lista: Lista) {
        return listaDAO.deleteList(lista.toListaEntity())
    }


}