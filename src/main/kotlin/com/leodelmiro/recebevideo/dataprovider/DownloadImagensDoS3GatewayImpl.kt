package com.leodelmiro.recebevideo.dataprovider

import com.leodelmiro.recebevideo.core.dataprovider.DownloadImagensDoS3Gateway
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request
import java.io.ByteArrayOutputStream

@Component
class DownloadImagensDoS3GatewayImpl(
    private val amazonS3Client: S3Client,
    @Value("\${amazon.s3.bucket}")
    private val bucketName: String
) : DownloadImagensDoS3Gateway {

    override fun executar(prefix: String): List<Pair<String, ByteArray>> {
        val imagens: MutableList<Pair<String, ByteArray>> = mutableListOf()

        val listRequest = ListObjectsV2Request.builder()
            .bucket(bucketName)
            .prefix(prefix)
            .build()

        val listResponse = amazonS3Client.listObjectsV2(listRequest)

        listResponse.contents().forEach { s3Object ->
            val key = s3Object.key()
            val getRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build()

            val s3InputStream = amazonS3Client.getObject(getRequest)
            val outputStream = ByteArrayOutputStream()

            s3InputStream.use { it.copyTo(outputStream) }

            imagens.add(key to outputStream.toByteArray())
        }

        return imagens
    }
}
