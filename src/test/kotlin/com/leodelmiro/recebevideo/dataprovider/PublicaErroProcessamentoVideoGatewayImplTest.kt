package com.leodelmiro.recebevideo.dataprovider

import com.fasterxml.jackson.databind.ObjectMapper
import io.awspring.cloud.sqs.operations.SqsTemplate
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import utils.criaArquivo

class PublicaErroProcessamentoVideoGatewayImplTest {

    private val sqsTemplate: SqsTemplate = mock(SqsTemplate::class.java)
    private val objectMapper: ObjectMapper = ObjectMapper()
    private val publicaErroProcessamento: String = "test-queue"

    private val gateway = PublicaErroProcessamentoVideoGatewayImpl(
        sqsTemplate = sqsTemplate,
        publicaErroProcessamento = publicaErroProcessamento,
        objectMapper = objectMapper
    )

    @Test
    fun `deve enviar mensagem para a fila SQS`() {
        val arquivo = criaArquivo()
        val mensagemSerializada = objectMapper.writeValueAsString(arquivo)

        gateway.executar(arquivo)

        verify(sqsTemplate, times(1)).send(publicaErroProcessamento, mensagemSerializada)
    }
}