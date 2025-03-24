package com.leodelmiro.recebevideo.dataprovider.request

import com.leodelmiro.recebevideo.core.domain.Arquivo

data class NovoVideoRecebidoRequest(
    val nome: String,
    val descricao: String,
    val autor: String,
    val videoKey: String
) {
    constructor(arquivo: Arquivo, videoKey: String) : this(arquivo.nome, arquivo.descricao, arquivo.autor, videoKey)

    fun toVideo() = Arquivo(this.videoKey, this.nome, this.descricao, this.autor)
}