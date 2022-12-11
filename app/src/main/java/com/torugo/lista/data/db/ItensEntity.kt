package com.torugo.lista.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.torugo.lista.data.model.Item

@Entity(tableName = "itens")
data class ItensEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var nome: String,
    var descricao: String,
    var valor: Double,
    var idLista: Long,
    var estado: String,
    var categoria: String
)

fun Item.toItensEntity(): ItensEntity {
    return with(this) {
        ItensEntity(
            id = this.id,
            nome = this.nome,
            descricao = this.descricao,
            valor = this.valor,
            idLista = this.idLista,
            estado = this.estado,
            categoria = this.categoria
        )
    }
}

fun ItensEntity.toItem(): Item {
    return Item(
        id = this.id,
        nome = this.nome,
        descricao = this.descricao,
        valor = this.valor,
        idLista = this.idLista,
        estado = this.estado,
        categoria = this.categoria
    )
}

fun List<ItensEntity>.toListItens(): List<Item>{
    return this.map {
        it.toItem()
    }
}
