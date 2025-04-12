package com.leodelmiro.recebevideo.dataprovider

import com.leodelmiro.recebevideo.core.dataprovider.UploadImagemS3Gateway
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest

class UploadImagensS3ImplTest {

    private val amazonS3Client: S3Client = mock(S3Client::class.java)
    private val bucketName = "test-bucket"
    private val uploadImagemS3Gateway: UploadImagemS3Gateway = UploadImagensS3Impl(amazonS3Client, bucketName)

    @Test
    fun `deve fazer upload de uma imagem para o S3`() {
        val bytes = "imagem-teste".toByteArray()
        val nomeVideo = "video-teste"
        val frameNumero = 1

        uploadImagemS3Gateway.executar(bytes, nomeVideo, frameNumero)

        verify(amazonS3Client).putObject(any(PutObjectRequest::class.java), any(RequestBody::class.java))
    }
}