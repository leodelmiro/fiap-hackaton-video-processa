package com.leodelmiro.recebevideo.core.usecase.impl

import com.leodelmiro.recebevideo.core.dataprovider.DownloadArquivoGateway
import com.leodelmiro.recebevideo.core.dataprovider.PublicaProcessamentoFinalizadoGateway
import com.leodelmiro.recebevideo.core.dataprovider.UploadImagensZipGateway
import com.leodelmiro.recebevideo.core.domain.Arquivo
import com.leodelmiro.recebevideo.core.usecase.ProcessaVideoUseCase
import com.leodelmiro.recebevideo.core.usecase.RealizaZipImagensUseCase
import com.leodelmiro.recebevideo.core.usecase.TransformaVideoEmImagensUseCase
import com.leodelmiro.recebevideo.dataprovider.UploadImagensS3Impl

class ProcessaVideoUseCaseImpl(
    private val downloadVideoGateway: DownloadArquivoGateway,
    private val transformaVideoEmImagensUseCase: TransformaVideoEmImagensUseCase,
    private val realizaZipImagensUseCase: RealizaZipImagensUseCase,
    private val uploadImagensZipGateway: UploadImagensZipGateway,
    private val publicaProcessamentoFinalizadoGateway: PublicaProcessamentoFinalizadoGateway
) :
    ProcessaVideoUseCase {
    override fun executar(arquivo: Arquivo) {
        val file = downloadVideoGateway.executar(arquivo)
        val imagensKey = transformaVideoEmImagensUseCase.executar(file, arquivo.nome)
        val zip = realizaZipImagensUseCase.executar(imagensKey, arquivo.nome)
        val arquivoZip = uploadImagensZipGateway.executar(zip, arquivo)
        publicaProcessamentoFinalizadoGateway.executar(arquivoZip)
    }
}