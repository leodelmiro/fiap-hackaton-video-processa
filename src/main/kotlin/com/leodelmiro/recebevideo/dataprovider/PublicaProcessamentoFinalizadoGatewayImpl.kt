package com.leodelmiro.recebevideo.dataprovider

import com.fasterxml.jackson.databind.ObjectMapper
import com.leodelmiro.recebevideo.core.dataprovider.PublicaProcessamentoFinalizadoGateway
import com.leodelmiro.recebevideo.core.domain.Arquivo
import io.awspring.cloud.sqs.operations.SqsTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class PublicaProcessamentoFinalizadoGatewayImpl(
    private val sqsTemplate: SqsTemplate,
    @Value("\${spring.cloud.aws.sqs.queues.processamento-realizado}")
    private val novoVideoRecebido: String? = null,
    private val objectMapper: ObjectMapper,
) : PublicaProcessamentoFinalizadoGateway {

    override fun executar(arquivo: Arquivo) {
        sqsTemplate.send(novoVideoRecebido, objectMapper.writeValueAsString(arquivo))
    }
}