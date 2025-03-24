package com.leodelmiro.recebevideo.dataprovider.request

import com.leodelmiro.recebevideo.core.domain.Arquivo

data class VideProcessadoRequest(
    val nome: String,
    val descricao: String,
    val autor: String,
    val videoKey: String
) {
    constructor(arquivo: Arquivo) : this(arquivo.nome, arquivo.descricao, arquivo.autor, arquivo.key)
}