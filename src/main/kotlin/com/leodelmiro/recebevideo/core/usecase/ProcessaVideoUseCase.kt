package com.leodelmiro.recebevideo.core.usecase

import com.leodelmiro.recebevideo.core.domain.Arquivo

interface ProcessaVideoUseCase {
    fun executar(arquivo: Arquivo)
}