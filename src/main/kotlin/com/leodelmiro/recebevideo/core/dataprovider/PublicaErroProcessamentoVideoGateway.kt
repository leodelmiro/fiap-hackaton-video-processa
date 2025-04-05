package com.leodelmiro.recebevideo.core.dataprovider

import com.leodelmiro.recebevideo.core.domain.Arquivo

interface PublicaErroProcessamentoVideoGateway {
    fun executar(arquivo: Arquivo)
}