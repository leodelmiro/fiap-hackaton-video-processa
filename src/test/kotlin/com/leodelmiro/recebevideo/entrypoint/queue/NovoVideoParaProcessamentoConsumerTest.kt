package com.leodelmiro.recebevideo.entrypoint.queue

import com.fasterxml.jackson.databind.ObjectMapper
import com.leodelmiro.recebevideo.core.dataprovider.PublicaErroProcessamentoVideoGateway
import com.leodelmiro.recebevideo.core.domain.Arquivo
import com.leodelmiro.recebevideo.core.usecase.ProcessaVideoUseCase
import com.leodelmiro.recebevideo.entrypoint.queue.request.NovoVideoRecebidoRequest
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class NovoVideoParaProcessamentoConsumerTest {

    private val processaVideoUseCase: ProcessaVideoUseCase = mock(ProcessaVideoUseCase::class.java)
    private val publicaErroProcessamentoVideoGateway: PublicaErroProcessamentoVideoGateway = mock(PublicaErroProcessamentoVideoGateway::class.java)
    private val objectMapper: ObjectMapper = ObjectMapper().findAndRegisterModules()
    private val consumer = NovoVideoParaProcessamentoConsumer(
        processaVideoUseCase,
        publicaErroProcessamentoVideoGateway,
        objectMapper
    )

    @Test
    fun `deve processar video com sucesso`() {
        val payload = """{"Message":"{\"videoKey\":\"key123\",\"nome\":\"video.mp4\",\"descricao\":\"descricao\",\"autor\":\"autor\"}"}"""
        val videoRequest = NovoVideoRecebidoRequest("key123", "video.mp4", "descricao", "autor")

        consumer.escutaPagagamentoEfetuado(payload)

        verifyNoInteractions(publicaErroProcessamentoVideoGateway)
    }

    @Test
    fun `deve publicar erro ao falhar no processamento`() {
        val payload = """{"Message":"{\"nome\":\"video.mp4\",\"descricao\":\"descricao\",\"autor\":\"autor\",\"videoKey\":\"key123\"}"}"""
        val videoRequest = NovoVideoRecebidoRequest("video.mp4", "descricao", "autor", "key123")
        val arquivo = Arquivo("key123", "video.mp4", "descricao", "autor")

        doThrow(RuntimeException("Erro ao processar vídeo")).`when`(processaVideoUseCase).executar(videoRequest.toVideo())

        consumer.escutaPagagamentoEfetuado(payload)

        verify(publicaErroProcessamentoVideoGateway, times(1)).executar(arquivo)
    }
}