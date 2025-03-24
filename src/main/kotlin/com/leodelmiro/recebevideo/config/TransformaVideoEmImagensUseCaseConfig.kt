package com.leodelmiro.recebevideo.config

import com.leodelmiro.recebevideo.core.usecase.impl.TransformaVideoEmImagensUseCaseImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TransformaVideoEmImagensUseCaseConfig {
    @Bean
    fun transformaVideoEmImagensUseCase(): TransformaVideoEmImagensUseCaseImpl {
        return TransformaVideoEmImagensUseCaseImpl()
    }
}