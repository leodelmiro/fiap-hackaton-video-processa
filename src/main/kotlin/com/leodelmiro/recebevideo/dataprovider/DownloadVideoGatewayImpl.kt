package com.leodelmiro.recebevideo.dataprovider

import com.leodelmiro.recebevideo.core.dataprovider.DownloadVideoGateway
import com.leodelmiro.recebevideo.core.domain.Arquivo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import java.io.File
import java.io.FileOutputStream

@Component
class DownloadVideoGatewayImpl(
    @Autowired
    private val amazonS3Client: S3Client,
    @Value("\${amazon.s3.bucket}")
    private val bucketName: String? = null,
) : DownloadVideoGateway {

    override fun executar(arquivo: Arquivo): File {
        val file = File.createTempFile(arquivo.nome, ".tmp")

        val objectRequest = GetObjectRequest.builder()
            .key(arquivo.key)
            .bucket(bucketName)
            .build()

        val responseInputStream = amazonS3Client.getObject(objectRequest)

        FileOutputStream(file).use { outputStream ->
            responseInputStream.use { inputStream ->
                inputStream.copyTo(outputStream)
            }
        }

        return file
    }
}