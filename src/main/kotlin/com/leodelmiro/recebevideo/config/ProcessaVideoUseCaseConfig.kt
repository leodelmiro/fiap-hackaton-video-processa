package com.leodelmiro.recebevideo.config

import com.leodelmiro.recebevideo.core.usecase.impl.ProcessaVideoUseCaseImpl
import com.leodelmiro.recebevideo.core.usecase.impl.RealizaZipImagensUseCaseImpl
import com.leodelmiro.recebevideo.core.usecase.impl.TransformaVideoEmImagensUseCaseImpl
import com.leodelmiro.recebevideo.dataprovider.DownloadVideoGatewayImpl
import com.leodelmiro.recebevideo.dataprovider.PublicaProcessamentoFinalizadoGatewayImpl
import com.leodelmiro.recebevideo.dataprovider.UploadImagensZipGatewayImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProcessaVideoUseCaseConfig {
    @Bean
    fun processaVideoUseCase(
        downloadVideoGateway: DownloadVideoGatewayImpl,
        transformaVideoEmImagensUseCase: TransformaVideoEmImagensUseCaseImpl,
        realizaZipVideoUseCase: RealizaZipImagensUseCaseImpl,
        uploadImagensZipGateway: UploadImagensZipGatewayImpl,
        publicaProcessamentoFinalizadoGateway: PublicaProcessamentoFinalizadoGatewayImpl
    ): ProcessaVideoUseCaseImpl {
        return ProcessaVideoUseCaseImpl(
            downloadVideoGateway,
            transformaVideoEmImagensUseCase,
            realizaZipVideoUseCase,
            uploadImagensZipGateway,
            publicaProcessamentoFinalizadoGateway
        )
    }
}