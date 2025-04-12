package com.leodelmiro.recebevideo.core.usecase.impl

import com.leodelmiro.recebevideo.core.dataprovider.DownloadImagensDoS3Gateway
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import utils.criarImagem
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import java.util.zip.ZipInputStream

class RealizaZipImagensUseCaseImplTest {

    private val downloadImagensDoS3Gateway: DownloadImagensDoS3Gateway = mock(DownloadImagensDoS3Gateway::class.java)
    private val realizaZipImagensUseCase = RealizaZipImagensUseCaseImpl(downloadImagensDoS3Gateway)

    @Test
    fun `deve criar um arquivo ZIP com as imagens fornecidas`() {
        val imagens: List<Pair<String, ByteArray>> = listOf(
            "frame_0.png" to bufferedImageToByteArray(criarImagem(100, 100, Color.RED), "png"),
            "frame_1.png" to bufferedImageToByteArray(criarImagem(200, 200, Color.BLUE), "png"),
            "frame_2.png" to bufferedImageToByteArray(criarImagem(300, 300, Color.GREEN), "png")
        )
        val key = "videos/test/frames"

        `when`(downloadImagensDoS3Gateway.executar(key)).thenReturn(imagens)

        val zipBytes = realizaZipImagensUseCase.executar(key)

        assertNotNull(zipBytes)
        val zipEntries = lerEntradasDoZip(zipBytes)
        assertEquals(3, zipEntries.size)
        assertEquals("frame_0.png", zipEntries[0])
        assertEquals("frame_1.png", zipEntries[1])
        assertEquals("frame_2.png", zipEntries[2])
    }

    private fun bufferedImageToByteArray(image: BufferedImage, format: String): ByteArray {
        val outputStream = ByteArrayOutputStream()
        ImageIO.write(image, format, outputStream)
        return outputStream.toByteArray()
    }

    private fun lerEntradasDoZip(zipBytes: ByteArray): List<String> {
        val entradas = mutableListOf<String>()
        ZipInputStream(zipBytes.inputStream()).use { zipIn ->
            var entry = zipIn.nextEntry
            while (entry != null) {
                entradas.add(entry.name)
                entry = zipIn.nextEntry
            }
        }
        return entradas
    }
}