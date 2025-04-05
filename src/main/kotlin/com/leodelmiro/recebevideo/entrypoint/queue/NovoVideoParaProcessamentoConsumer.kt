package com.leodelmiro.recebevideo.entrypoint.queue

import com.fasterxml.jackson.databind.ObjectMapper
import com.leodelmiro.recebevideo.core.dataprovider.PublicaErroProcessamentoVideoGateway
import com.leodelmiro.recebevideo.core.domain.Arquivo
import com.leodelmiro.recebevideo.core.usecase.ProcessaVideoUseCase
import com.leodelmiro.recebevideo.entrypoint.queue.request.NovoVideoRecebidoRequest
import io.awspring.cloud.sqs.annotation.SqsListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class NovoVideoParaProcessamentoConsumer(
    private val processaVideoUseCase: ProcessaVideoUseCase,
    private val publicaErroProcessamentoVideoGateway: PublicaErroProcessamentoVideoGateway,
    private val objectMapper: ObjectMapper
) {

    private val logger: Logger = LoggerFactory.getLogger(NovoVideoParaProcessamentoConsumer::class.java)


    @SqsListener("\${spring.cloud.aws.sqs.queues.processa-video}")
    fun escutaPagagamentoEfetuado(@Payload payload: String) {
        val message = objectMapper.readTree(payload)["Message"].asText()
        logger.info("Recebido novo video para processamento: $message")
        val videoMessage = objectMapper.readValue(message, NovoVideoRecebidoRequest::class.java)
        try {
            processaVideoUseCase.executar(videoMessage.toVideo())
        } catch (exception: Exception) {
            publicaErroProcessamentoVideoGateway.executar(
                Arquivo(
                    videoMessage.videoKey,
                    videoMessage.nome,
                    videoMessage.descricao,
                    videoMessage.autor
                )
            )
        }
    }
}
