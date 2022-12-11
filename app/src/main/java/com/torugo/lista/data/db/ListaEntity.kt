package com.torugo.lista.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.torugo.lista.data.model.Lista

@Entity(tableName = "listas")
data class ListaEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var nome: String,
    var texto: String,
    var total: Double,
    var dataCriacao: String,
    var dataFinalizacao: String
)

fun Lista.toListaEntity(): ListaEntity {
    return with(this) {
        ListaEntity(
            id = this.id,
            nome = this.nome,
            texto = this.texto,
            total = this.total,
            dataCriacao = this.dataCriacao,
            dataFinalizacao = this.dataFinalizacao
        )
    }
}

fun ListaEntity.toLista(): Lista {
    return Lista(
        id = this.id,
        nome = this.nome,
        texto = this.texto,
        total = this.total,
        dataCriacao = this.dataCriacao,
        dataFinalizacao = this.dataFinalizacao
    )
}

fun List<ListaEntity>.toListLista(): List<Lista>{
    return this.map {
      it.toLista()
    }
}
