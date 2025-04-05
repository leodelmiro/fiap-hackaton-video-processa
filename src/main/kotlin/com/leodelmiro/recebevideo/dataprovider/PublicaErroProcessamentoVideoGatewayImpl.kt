package com.leodelmiro.recebevideo.dataprovider

import com.fasterxml.jackson.databind.ObjectMapper
import com.leodelmiro.recebevideo.core.dataprovider.PublicaErroProcessamentoVideoGateway
import com.leodelmiro.recebevideo.core.domain.Arquivo
import io.awspring.cloud.sqs.operations.SqsTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class PublicaErroProcessamentoVideoGatewayImpl(
    private val sqsTemplate: SqsTemplate,
    @Value("\${spring.cloud.aws.sqs.queues.erro-processamento}")
    private val publicaErroProcessamento: String? = null,
    private val objectMapper: ObjectMapper,
) : PublicaErroProcessamentoVideoGateway {

    override fun executar(arquivo: Arquivo) {
        sqsTemplate.send(publicaErroProcessamento, objectMapper.writeValueAsString(arquivo))
    }
}