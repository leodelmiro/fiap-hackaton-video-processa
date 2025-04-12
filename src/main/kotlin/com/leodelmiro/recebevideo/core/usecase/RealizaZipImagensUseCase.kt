package com.leodelmiro.recebevideo.core.usecase

import java.io.File


interface RealizaZipImagensUseCase {
    fun executar(imagensKey: String, arquivoNome: String): File
}