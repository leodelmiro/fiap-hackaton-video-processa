package com.leodelmiro.recebevideo.core.dataprovider

import com.leodelmiro.recebevideo.core.domain.Arquivo
import java.io.File

interface DownloadVideoGateway {
    fun executar(arquivo: Arquivo): File
}