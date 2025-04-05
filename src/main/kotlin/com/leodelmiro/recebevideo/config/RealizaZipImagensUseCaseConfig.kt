package com.leodelmiro.recebevideo.config

import com.leodelmiro.recebevideo.core.usecase.impl.RealizaZipImagensUseCaseImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RealizaZipImagensUseCaseConfig {
    @Bean
    fun realizaZipImagensUseCase(): RealizaZipImagensUseCaseImpl {
        return RealizaZipImagensUseCaseImpl()
    }
}