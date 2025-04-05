package com.leodelmiro.recebevideo.core.usecase.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import utils.criarImagem
import java.awt.Color
import java.awt.image.BufferedImage
import java.util.zip.ZipInputStream

class RealizaZipImagensUseCaseImplTest {

    private val realizaZipImagensUseCase = RealizaZipImagensUseCaseImpl()

    @Test
    fun `deve criar um arquivo ZIP com as imagens fornecidas`() {
        val imagens = listOf(
            criarImagem(100, 100, Color.RED),
            criarImagem(200, 200, Color.BLUE),
            criarImagem(300, 300, Color.GREEN)
        )

        val zipBytes = realizaZipImagensUseCase.executar(imagens)

        assertNotNull(zipBytes)
        val zipEntries = lerEntradasDoZip(zipBytes)
        assertEquals(3, zipEntries.size)
        assertEquals("frame_0.png", zipEntries[0])
        assertEquals("frame_1.png", zipEntries[1])
        assertEquals("frame_2.png", zipEntries[2])
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