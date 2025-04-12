package com.leodelmiro.recebevideo.core.dataprovider

import com.leodelmiro.recebevideo.core.domain.Arquivo
import java.io.File

interface UploadImagensZipGateway {
    fun executar(arquivo: File, antigoArquivo: Arquivo): Arquivo
}