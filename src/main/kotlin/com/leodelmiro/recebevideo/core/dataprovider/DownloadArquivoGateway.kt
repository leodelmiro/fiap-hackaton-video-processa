package com.leodelmiro.recebevideo.core.dataprovider

import com.leodelmiro.recebevideo.core.domain.Arquivo
import java.io.File

interface DownloadArquivoGateway {
    fun executar(arquivo: Arquivo): File
}