package com.leodelmiro.recebevideo.core.dataprovider

import com.leodelmiro.recebevideo.core.domain.Arquivo

interface PublicaProcessamentoFinalizadoGateway {
    fun executar(arquivo: Arquivo)
}