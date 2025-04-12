package com.leodelmiro.recebevideo.dataprovider

import com.leodelmiro.recebevideo.core.dataprovider.DownloadImagensDoS3Gateway
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import software.amazon.awssdk.core.ResponseInputStream
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.*
import utils.criaArquivo
import java.io.ByteArrayInputStream

class DownloadImagensDoS3GatewayImplTest {

    private val amazonS3Client: S3Client = mock(S3Client::class.java)
    private val bucketName = "test-bucket"
    private val downloadImagensDoS3Gateway: DownloadImagensDoS3Gateway =
        DownloadImagensDoS3GatewayImpl(amazonS3Client, bucketName)


    @Test
    fun `deve retornar lista de imagens com chave e bytes`() {
        val s3Object1 = S3Object.builder().key("image1.png").build()
        val s3Object2 = S3Object.builder().key("image2.png").build()
        val arquivo = criaArquivo()
        val fileContent = "conteúdo do arquivo".toByteArray()
        val inputStream = ByteArrayInputStream(fileContent)

        val getObjectResponse = mock(GetObjectResponse::class.java)
        val responseInputStream = ResponseInputStream(getObjectResponse, ByteArrayInputStream("image1-content".toByteArray()))
        val responseInputStream2 = ResponseInputStream(getObjectResponse, ByteArrayInputStream("image2-content".toByteArray()))
        val listResponse = ListObjectsV2Response.builder()
            .contents(listOf(s3Object1, s3Object2))
            .build()
        `when`(
            amazonS3Client.listObjectsV2(
                ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .prefix("test-prefix")
                    .build()
            )
        ).thenReturn(listResponse)

        val image1Bytes = "image1-content".toByteArray()
        val image2Bytes = "image2-content".toByteArray()

        `when`(
            amazonS3Client.getObject(
                GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key("image1.png")
                    .build()
            )
        ).thenReturn(responseInputStream)

        `when`(
            amazonS3Client.getObject(
                GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key("image2.png")
                    .build()
            )
        ).thenReturn(responseInputStream2)

        val result = downloadImagensDoS3Gateway.executar("test-prefix")

        assertEquals(2, result.size)
        assertEquals("image1.png", result[0].first)
        assertEquals("image2.png", result[1].first)
        assertEquals(image1Bytes.toList(), result[0].second.toList())
        assertEquals(image2Bytes.toList(), result[1].second.toList())

        verify(amazonS3Client).listObjectsV2(any(ListObjectsV2Request::class.java))
        verify(amazonS3Client, times(2)).getObject(any(GetObjectRequest::class.java))
    }
}