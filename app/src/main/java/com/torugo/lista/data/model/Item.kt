package com.torugo.lista.data.model

data class Item(
    var id: Long,
    var nome: String,
    var descricao: String,
    var valor: Double,
    var idLista: Long,
    var estado: String,
    var categoria: String
)
