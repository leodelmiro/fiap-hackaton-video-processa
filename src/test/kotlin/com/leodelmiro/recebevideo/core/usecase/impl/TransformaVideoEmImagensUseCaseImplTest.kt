package com.leodelmiro.recebevideo.core.usecase.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

class TransformaVideoEmImagensUseCaseImplTest {
    private val arquivosParaDeletar = mutableListOf<File>()
    private val transformaVideoEmImagensUseCase = TransformaVideoEmImagensUseCaseImpl()

    @Test
    fun `deve transformar video em lista de imagens`() {
        val arquivo = File(ClassLoader.getSystemResource("video.mp4").file)

        val imagens = transformaVideoEmImagensUseCase.executar(arquivo)

        assertEquals(30, imagens.size)
    }
}