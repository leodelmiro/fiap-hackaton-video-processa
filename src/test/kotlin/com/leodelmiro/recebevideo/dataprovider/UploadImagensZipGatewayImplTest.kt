package com.leodelmiro.recebevideo.dataprovider

import com.leodelmiro.recebevideo.core.domain.Arquivo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Utilities
import software.amazon.awssdk.services.s3.model.GetUrlRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectResponse
import utils.criaArquivo
import utils.criaMp4File
import java.net.URL

class UploadImagensZipGatewayImplTest {

    private lateinit var s3Client: S3Client
    private lateinit var uploadImagensZipGatewayImpl: UploadImagensZipGatewayImpl

    @BeforeEach
    fun setUp() {
        s3Client = mock(S3Client::class.java)
        uploadImagensZipGatewayImpl = UploadImagensZipGatewayImpl(
            amazonS3Client = s3Client,
            bucketName = "test-bucket"
        )
    }

    @Test
    fun `deve fazer upload do arquivo zip e retornar objeto Arquivo`() {
        val antigoArquivo = Arquivo(
            key = "videos/arquivo.mp4",
            nome = "arquivo",
            descricao = "Descrição do arquivo",
            autor = "Autor",
            url = "https://old-url.com"
        )
        val zipContent = criaMp4File()
        val s3Utilities = mock(S3Utilities::class.java)
        val putObjectResponse = mock(PutObjectResponse::class.java)
        val url = URL("https://s3.amazonaws.com/test-bucket/zips/arquivo.zip")

        `when`(s3Client.putObject(any(PutObjectRequest::class.java), any(RequestBody::class.java)))
            .thenReturn(putObjectResponse)
        `when`(s3Client.utilities()).thenReturn(s3Utilities)
        `when`(s3Utilities.getUrl(any(GetUrlRequest::class.java))).thenReturn(url)

        val arquivoRetornado = uploadImagensZipGatewayImpl.executar(zipContent, antigoArquivo)

        assertEquals("zips/arquivo.zip", arquivoRetornado.key)
        assertEquals("arquivo", arquivoRetornado.nome)
        assertEquals("Descrição do arquivo", arquivoRetornado.descricao)
        assertEquals("Autor", arquivoRetornado.autor)
        assertEquals(url.toExternalForm(), arquivoRetornado.url)

        val captor = ArgumentCaptor.forClass(PutObjectRequest::class.java)
        verify(s3Client).putObject(captor.capture(), any(RequestBody::class.java))
        val request = captor.value

        assertEquals("test-bucket", request.bucket())
        assertEquals("zips/arquivo.zip", request.key())
        assertEquals("application/zip", request.contentType())
    }
}
