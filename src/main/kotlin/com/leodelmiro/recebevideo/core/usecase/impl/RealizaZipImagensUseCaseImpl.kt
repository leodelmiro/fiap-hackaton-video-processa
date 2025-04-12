package com.leodelmiro.recebevideo.core.usecase.impl

import com.leodelmiro.recebevideo.core.dataprovider.DownloadEZipImagensGateway
import com.leodelmiro.recebevideo.core.usecase.RealizaZipImagensUseCase
import java.io.ByteArrayOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class RealizaZipImagensUseCaseImpl(
    private val downloadEZipImagensGateway: DownloadEZipImagensGateway
) : RealizaZipImagensUseCase {

    override fun executar(prefix: String): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()

        ZipOutputStream(byteArrayOutputStream).use { zipOut ->
            downloadEZipImagensGateway.executar(prefix) { key, inputStream ->
                val entryName = key.removePrefix("videos/")
                zipOut.putNextEntry(ZipEntry(entryName))
                inputStream.copyTo(zipOut)
                zipOut.closeEntry()
            }
        }

        return byteArrayOutputStream.toByteArray()
    }
}
