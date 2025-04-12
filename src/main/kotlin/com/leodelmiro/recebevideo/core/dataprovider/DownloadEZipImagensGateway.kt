package com.leodelmiro.recebevideo.core.dataprovider

import java.io.InputStream

interface DownloadEZipImagensGateway {
    fun executar(prefix: String, stream: (key: String, inputStream: InputStream) -> Unit)

}
