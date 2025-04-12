package com.leodelmiro.recebevideo.core.dataprovider

interface UploadImagemS3Gateway {
    fun executar(bytes: ByteArray, nomeVideo: String, frameNumero: Int)
}