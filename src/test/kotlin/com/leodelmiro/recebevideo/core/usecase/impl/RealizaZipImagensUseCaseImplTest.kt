package com.leodelmiro.recebevideo.core.usecase.impl

import com.leodelmiro.recebevideo.core.dataprovider.DownloadEZipImagensGateway
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

class RealizaZipImagensUseCaseImplTest {

    private val downloadAndZipImagensGateway: DownloadEZipImagensGateway = mock(DownloadEZipImagensGateway::class.java)
    private val realizaZipImagensUseCase = RealizaZipImagensUseCaseImpl(downloadAndZipImagensGateway)

    @Test
    fun `deve criar um arquivo ZIP com as imagens fornecidas`() {
        val key = "videos/test/frames"

        val zipBytes = realizaZipImagensUseCase.executar(key)

        assertNotNull(zipBytes)
    }
}