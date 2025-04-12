package com.leodelmiro.recebevideo.core.dataprovider

interface DownloadImagensDoS3Gateway {
    fun executar(prefix: String): List<Pair<String, ByteArray>>
}
