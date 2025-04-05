package com.leodelmiro.recebevideo.core.usecase.impl

import com.leodelmiro.recebevideo.core.dataprovider.DownloadVideoGateway
import com.leodelmiro.recebevideo.core.dataprovider.PublicaProcessamentoFinalizadoGateway
import com.leodelmiro.recebevideo.core.dataprovider.UploadImagensZipGateway
import com.leodelmiro.recebevideo.core.usecase.RealizaZipImagensUseCase
import com.leodelmiro.recebevideo.core.usecase.TransformaVideoEmImagensUseCase
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import utils.criaArquivo
import utils.criarImagem
import java.awt.Color
import java.io.File

class ProcessaVideoUseCaseImplTest {

    private val downloadVideoGateway: DownloadVideoGateway = mock(DownloadVideoGateway::class.java)
    private val transformaVideoEmImagensUseCase: TransformaVideoEmImagensUseCase =
        mock(TransformaVideoEmImagensUseCase::class.java)
    private val realizaZipImagensUseCase: RealizaZipImagensUseCase = mock(RealizaZipImagensUseCase::class.java)
    private val uploadImagensZipGateway: UploadImagensZipGateway = mock(UploadImagensZipGateway::class.java)
    private val publicaProcessamentoFinalizadoGateway: PublicaProcessamentoFinalizadoGateway =
        mock(PublicaProcessamentoFinalizadoGateway::class.java)

    private val processaVideoUseCase = ProcessaVideoUseCaseImpl(
        downloadVideoGateway,
        transformaVideoEmImagensUseCase,
        realizaZipImagensUseCase,
        uploadImagensZipGateway,
        publicaProcessamentoFinalizadoGateway
    )

    @Test
    fun `deve processar o video corretamente`() {
        val arquivo = criaArquivo()
        val fileMock = mock(File::class.java)
        val imagens = listOf(
            criarImagem(100, 100, Color.RED),
            criarImagem(200, 200, Color.BLUE),
            criarImagem(300, 300, Color.GREEN)
        )
        val zipMock = ByteArray(1024)
        val arquivoZipMock = criaArquivo()

        `when`(downloadVideoGateway.executar(arquivo)).thenReturn(fileMock)
        `when`(transformaVideoEmImagensUseCase.executar(fileMock)).thenReturn(imagens)
        `when`(realizaZipImagensUseCase.executar(imagens)).thenReturn(zipMock)
        `when`(uploadImagensZipGateway.executar(zipMock, arquivo)).thenReturn(arquivoZipMock)

        processaVideoUseCase.executar(arquivo)

        verify(downloadVideoGateway).executar(arquivo)
        verify(transformaVideoEmImagensUseCase).executar(fileMock)
        verify(realizaZipImagensUseCase).executar(imagens)
        verify(uploadImagensZipGateway).executar(zipMock, arquivo)
        verify(publicaProcessamentoFinalizadoGateway).executar(arquivoZipMock)
    }
}