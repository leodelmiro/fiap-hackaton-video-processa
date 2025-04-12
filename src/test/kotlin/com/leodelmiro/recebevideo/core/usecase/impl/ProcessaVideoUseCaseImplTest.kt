package com.leodelmiro.recebevideo.core.usecase.impl

import com.leodelmiro.recebevideo.core.dataprovider.DownloadArquivoGateway
import com.leodelmiro.recebevideo.core.dataprovider.PublicaProcessamentoFinalizadoGateway
import com.leodelmiro.recebevideo.core.dataprovider.UploadImagensZipGateway
import com.leodelmiro.recebevideo.core.usecase.RealizaZipImagensUseCase
import com.leodelmiro.recebevideo.core.usecase.TransformaVideoEmImagensUseCase
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import utils.criaArquivo
import utils.criaMp4File
import utils.criarImagem
import java.awt.Color
import java.io.File

class ProcessaVideoUseCaseImplTest {

    private val downloadVideoGateway: DownloadArquivoGateway = mock(DownloadArquivoGateway::class.java)
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
        val zipMock = criaMp4File()
        val arquivoZipMock = criaArquivo()
        val key = "videos/${arquivo.nome}/frames"

        `when`(downloadVideoGateway.executar(arquivo)).thenReturn(fileMock)
        `when`(transformaVideoEmImagensUseCase.executar(fileMock, arquivo.nome)).thenReturn(key)
        `when`(realizaZipImagensUseCase.executar(key, arquivo.nome)).thenReturn(zipMock)
        `when`(uploadImagensZipGateway.executar(zipMock, arquivo)).thenReturn(arquivoZipMock)

        processaVideoUseCase.executar(arquivo)

        verify(downloadVideoGateway).executar(arquivo)
        verify(transformaVideoEmImagensUseCase).executar(fileMock, arquivo.nome)
        verify(realizaZipImagensUseCase).executar(key, arquivo.nome)
        verify(uploadImagensZipGateway).executar(zipMock, arquivo)
        verify(publicaProcessamentoFinalizadoGateway).executar(arquivoZipMock)
    }
}