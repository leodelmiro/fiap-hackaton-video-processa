package com.leodelmiro.recebevideo.entrypoint.queue.request

import com.leodelmiro.recebevideo.core.domain.Arquivo

data class NovoVideoRecebidoRequest(
    val nome: String,
    val descricao: String,
    val autor: String,
    val videoKey: String
) {
    fun toVideo() = Arquivo(this.videoKey, this.nome, this.descricao, this.autor)
}