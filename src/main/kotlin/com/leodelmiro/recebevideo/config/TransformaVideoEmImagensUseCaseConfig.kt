package com.leodelmiro.recebevideo.config

import com.leodelmiro.recebevideo.core.usecase.impl.TransformaVideoEmImagensUseCaseImpl
import com.leodelmiro.recebevideo.dataprovider.UploadImagensS3Impl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TransformaVideoEmImagensUseCaseConfig {
    @Bean
    fun transformaVideoEmImagensUseCase(uploadImagensS3Impl: UploadImagensS3Impl): TransformaVideoEmImagensUseCaseImpl {
        return TransformaVideoEmImagensUseCaseImpl(uploadImagensS3Impl)
    }
}