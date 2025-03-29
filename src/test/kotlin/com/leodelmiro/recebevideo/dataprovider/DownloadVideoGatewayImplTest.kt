package com.leodelmiro.recebevideo.dataprovider

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import software.amazon.awssdk.core.ResponseInputStream
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectResponse
import utils.criaArquivo
import java.io.ByteArrayInputStream
import java.io.File
import java.nio.file.Path
import kotlin.test.assertEquals

class DownloadVideoGatewayImplTest {

    private lateinit var s3Client: S3Client
    private lateinit var downloadVideoGatewayImpl: DownloadVideoGatewayImpl

    @TempDir
    lateinit var tempDir: Path

    @BeforeEach
    fun setUp() {
        s3Client = mock(S3Client::class.java)
        downloadVideoGatewayImpl = DownloadVideoGatewayImpl(
            amazonS3Client = s3Client,
            bucketName = "test-bucket"
        )
    }

    @Test
    fun `deve fazer download do arquivo e salvar no sistema de arquivos`() {
        val arquivo = criaArquivo()
        val fileContent = "conteúdo do arquivo".toByteArray()
        val inputStream = ByteArrayInputStream(fileContent)

        val getObjectResponse = mock(GetObjectResponse::class.java)
        val responseInputStream = ResponseInputStream(getObjectResponse, inputStream)

        `when`(s3Client.getObject(any(GetObjectRequest::class.java)))
            .thenReturn(responseInputStream)

        val arquivoBaixado: File = downloadVideoGatewayImpl.executar(arquivo)

        assertTrue(arquivoBaixado.exists())
        assertTrue(arquivoBaixado.readText() == "conteúdo do arquivo")

        val captor = ArgumentCaptor.forClass(GetObjectRequest::class.java)
        verify(s3Client).getObject(captor.capture())

        val request = captor.value
        assertEquals("video.mp4", request.key())
        assertEquals("test-bucket", request.bucket())
    }
}
