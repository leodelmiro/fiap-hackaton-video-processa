package com.leodelmiro.recebevideo.dataprovider

import com.leodelmiro.recebevideo.core.dataprovider.UploadImagemS3Gateway
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest


@Component
class UploadImagensS3Impl(
    private val amazonS3Client: S3Client,
    @Value("\${amazon.s3.bucket}")
    private val bucketName: String? = null,
) : UploadImagemS3Gateway {

    override fun executar(bytes: ByteArray, nomeVideo: String, frameNumero: Int) {
        val prefix = "videos/${nomeVideo}/frames/"
        val key = "${prefix}frame_$frameNumero.png"

        val request = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .contentType("image/png")
            .contentLength(bytes.size.toLong())
            .build()

        amazonS3Client.putObject(request, RequestBody.fromBytes(bytes))
    }
}