package com.leodelmiro.recebevideo.core.usecase.impl

import com.leodelmiro.recebevideo.core.dataprovider.DownloadVideoGateway
import com.leodelmiro.recebevideo.core.dataprovider.PublicaProcessamentoFinalizadoGateway
import com.leodelmiro.recebevideo.core.dataprovider.UploadImagensZipGateway
import com.leodelmiro.recebevideo.core.domain.Arquivo
import com.leodelmiro.recebevideo.core.usecase.ProcessaVideoUseCase
import com.leodelmiro.recebevideo.core.usecase.RealizaZipImagensUseCase
import com.leodelmiro.recebevideo.core.usecase.TransformaVideoEmImagensUseCase

class ProcessaVideoUseCaseImpl(
    private val downloadVideoGateway: DownloadVideoGateway,
    private val transformaVideoEmImagensUseCase: TransformaVideoEmImagensUseCase,
    private val realizaZipImagensUseCase: RealizaZipImagensUseCase,
    private val uploadImagensZipGateway: UploadImagensZipGateway,
    private val publicaProcessamentoFinalizadoGateway: PublicaProcessamentoFinalizadoGateway
) :
    ProcessaVideoUseCase {
    override fun executar(arquivo: Arquivo) {
        val file = downloadVideoGateway.executar(arquivo)
        val imagens = transformaVideoEmImagensUseCase.executar(file)
        val zip = realizaZipImagensUseCase.executar(imagens)
        val arquivoZip = uploadImagensZipGateway.executar(zip, arquivo)
        publicaProcessamentoFinalizadoGateway.executar(arquivoZip)
    }
}