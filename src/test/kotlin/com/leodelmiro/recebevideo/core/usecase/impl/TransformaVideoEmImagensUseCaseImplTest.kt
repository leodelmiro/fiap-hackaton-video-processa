package com.leodelmiro.recebevideo.core.usecase.impl

import com.leodelmiro.recebevideo.core.dataprovider.UploadImagensZipGateway
import com.leodelmiro.recebevideo.dataprovider.UploadImagensS3Impl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import java.io.File

class TransformaVideoEmImagensUseCaseImplTest {
    private val uploadImagensS3Impl: UploadImagensS3Impl = mock(UploadImagensS3Impl::class.java)
    private val transformaVideoEmImagensUseCase = TransformaVideoEmImagensUseCaseImpl(uploadImagensS3Impl)

    @Test
    fun `deve transformar video em lista de imagens`() {
        val arquivo = File(ClassLoader.getSystemResource("video.mp4").file)

        val imagemKey = transformaVideoEmImagensUseCase.executar(arquivo, "video")

        assertEquals("videos/video/frames", imagemKey)
    }
}