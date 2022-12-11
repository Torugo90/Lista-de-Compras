package com.torugo.lista.data.model

data class Lista(
    var id: Long,
    var nome: String,
    var texto: String,
    var total: Double,
    var dataCriacao: String,
    var dataFinalizacao: String
)
