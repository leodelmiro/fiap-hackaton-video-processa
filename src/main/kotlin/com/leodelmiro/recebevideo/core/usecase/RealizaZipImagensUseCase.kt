package com.leodelmiro.recebevideo.core.usecase


interface RealizaZipImagensUseCase {
    fun executar(imagensKey: String): ByteArray
}