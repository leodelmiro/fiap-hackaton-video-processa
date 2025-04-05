package com.leodelmiro.recebevideo.core.dataprovider

import com.leodelmiro.recebevideo.core.domain.Arquivo

interface UploadImagensZipGateway {
    fun executar(zip: ByteArray, antigoArquivo: Arquivo): Arquivo
}