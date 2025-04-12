package com.leodelmiro.recebevideo.dataprovider

import com.leodelmiro.recebevideo.core.dataprovider.DownloadEZipImagensGateway
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request
import java.io.InputStream

@Component
class DownloadEZipImagensGatewayImpl(
    private val amazonS3Client: S3Client,
    @Value("\${amazon.s3.bucket}")
    private val bucketName: String
) : DownloadEZipImagensGateway {

    override fun executar(prefix: String, stream: (String, InputStream) -> Unit) {
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

            val s3ObjectStream = amazonS3Client.getObject(getRequest)
            stream(key, s3ObjectStream)
            s3ObjectStream.close()
        }
    }
}
