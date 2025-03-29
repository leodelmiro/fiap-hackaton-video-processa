package com.leodelmiro.recebevideo.core.domain

data class Arquivo(
    val key: String,
    val nome: String,
    val descricao: String,
    val autor: String,
    val url: String? = null
)