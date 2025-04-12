package com.leodelmiro.recebevideo.core.usecase.impl

import com.leodelmiro.recebevideo.core.dataprovider.DownloadImagensDoS3Gateway
import com.leodelmiro.recebevideo.core.usecase.RealizaZipImagensUseCase
import java.io.ByteArrayOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class RealizaZipImagensUseCaseImpl(
    private val downloadImagensDoS3Gateway: DownloadImagensDoS3Gateway
) : RealizaZipImagensUseCase {

    override fun executar(prefix: String): ByteArray {
        val imagens = downloadImagensDoS3Gateway.executar(prefix)

        val byteArrayOutputStream = ByteArrayOutputStream()
        ZipOutputStream(byteArrayOutputStream).use { zipOut ->
            imagens.forEachIndexed { _, (key, bytes) ->
                val entryName = key.removePrefix("videos/")
                zipOut.putNextEntry(ZipEntry(entryName))
                zipOut.write(bytes)
                zipOut.closeEntry()
            }
        }

        return byteArrayOutputStream.toByteArray()
    }
}
