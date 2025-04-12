package com.leodelmiro.recebevideo.config

import com.leodelmiro.recebevideo.core.dataprovider.DownloadEZipImagensGateway
import com.leodelmiro.recebevideo.core.usecase.impl.RealizaZipImagensUseCaseImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RealizaZipImagensUseCaseConfig {
    @Bean
    fun realizaZipImagensUseCase(downloadAndZipImagensGateway: DownloadEZipImagensGateway): RealizaZipImagensUseCaseImpl {
        return RealizaZipImagensUseCaseImpl(downloadAndZipImagensGateway)
    }
}