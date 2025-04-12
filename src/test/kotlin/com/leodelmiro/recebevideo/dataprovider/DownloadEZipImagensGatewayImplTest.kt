package com.leodelmiro.recebevideo.dataprovider

import com.leodelmiro.recebevideo.core.dataprovider.DownloadEZipImagensGateway
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import software.amazon.awssdk.core.ResponseInputStream
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.*
import java.io.ByteArrayInputStream

class DownloadEZipImagensGatewayImplTest {

    private val amazonS3Client: S3Client = mock(S3Client::class.java)
    private val bucketName = "test-bucket"
    private val downloadEZipImagensGateway: DownloadEZipImagensGateway =
        DownloadEZipImagensGatewayImpl(amazonS3Client, bucketName)

    @Test
    fun `deve executar stream para cada objeto no S3`() {
        val s3Object1 = S3Object.builder().key("image1.png").build()
        val s3Object2 = S3Object.builder().key("image2.png").build()

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
        val getObjectResponse = mock(GetObjectResponse::class.java)
        val responseInputStream1 = ResponseInputStream(getObjectResponse, ByteArrayInputStream(image1Bytes))
        val responseInputStream2 = ResponseInputStream(getObjectResponse, ByteArrayInputStream(image2Bytes))

        `when`(
            amazonS3Client.getObject(
                GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key("image1.png")
                    .build()
            )
        ).thenReturn(responseInputStream1)

        `when`(
            amazonS3Client.getObject(
                GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key("image2.png")
                    .build()
            )
        ).thenReturn(responseInputStream2)

        val capturedResults = mutableListOf<Pair<String, ByteArray>>()

        downloadEZipImagensGateway.executar("test-prefix") { key, inputStream ->
            val content = inputStream.readBytes()
            capturedResults.add(key to content)
        }

        assertEquals(2, capturedResults.size)
        assertEquals("image1.png", capturedResults[0].first)
        assertEquals("image2.png", capturedResults[1].first)
        assertEquals(image1Bytes.toList(), capturedResults[0].second.toList())
        assertEquals(image2Bytes.toList(), capturedResults[1].second.toList())

        // Verificando interações com o mock
        verify(amazonS3Client).listObjectsV2(any(ListObjectsV2Request::class.java))
        verify(amazonS3Client, times(2)).getObject(any(GetObjectRequest::class.java))
    }
}