package com.leodelmiro.recebevideo.core.usecase

import java.io.File

interface TransformaVideoEmImagensUseCase {
    fun executar(arquivo: File, arquivoNome: String): String
}