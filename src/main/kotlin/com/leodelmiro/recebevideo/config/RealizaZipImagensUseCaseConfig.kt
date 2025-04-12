package com.leodelmiro.recebevideo.config

import com.leodelmiro.recebevideo.core.dataprovider.DownloadImagensDoS3Gateway
import com.leodelmiro.recebevideo.core.usecase.impl.RealizaZipImagensUseCaseImpl
import com.leodelmiro.recebevideo.dataprovider.UploadImagensS3Impl
import com.leodelmiro.recebevideo.dataprovider.UploadImagensZipGatewayImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RealizaZipImagensUseCaseConfig {
    @Bean
    fun realizaZipImagensUseCase(downloadImagensDoS3Gateway: DownloadImagensDoS3Gateway): RealizaZipImagensUseCaseImpl {
        return RealizaZipImagensUseCaseImpl(downloadImagensDoS3Gateway)
    }
}