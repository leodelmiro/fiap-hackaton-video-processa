package com.leodelmiro.recebevideo.dataprovider

import com.leodelmiro.recebevideo.core.dataprovider.UploadImagensZipGateway
import com.leodelmiro.recebevideo.core.domain.Arquivo
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetUrlRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest


@Component
class UploadImagensZipGatewayImpl(
    private val amazonS3Client: S3Client,
    @Value("\${amazon.s3.bucket}")
    private val bucketName: String? = null,
) : UploadImagensZipGateway {


    override fun executar(zip: ByteArray, antigoArquivo: Arquivo): Arquivo {
        val nomeArquivoZip = "${antigoArquivo.nome}.zip"
        val key = "zips/$nomeArquivoZip"

        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .contentType("application/zip")
            .build()

        val requestBody = RequestBody.fromInputStream(zip.inputStream(), zip.size.toLong())
        amazonS3Client.putObject(putObjectRequest, requestBody)
        val request = GetUrlRequest.builder().bucket(bucketName).key(key).build()
        val url: String = amazonS3Client.utilities().getUrl(request).toExternalForm()


        return Arquivo(key, antigoArquivo.nome, antigoArquivo.descricao, antigoArquivo.autor, url)
    }
}