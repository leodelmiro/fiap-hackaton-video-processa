package com.leodelmiro.recebevideo.core.usecase.impl

import com.leodelmiro.recebevideo.core.usecase.RealizaZipImagensUseCase
import com.leodelmiro.recebevideo.entrypoint.queue.NovoVideoParaProcessamentoConsumer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import javax.imageio.ImageIO

class RealizaZipImagensUseCaseImpl : RealizaZipImagensUseCase {

    private val logger: Logger = LoggerFactory.getLogger(NovoVideoParaProcessamentoConsumer::class.java)

    override fun executar(imagens: List<BufferedImage>): ByteArray {
        logger.info("Iniciando criação de arquivo ZIP com ${imagens.size} imagens.")

        val byteArrayOutputStream = ByteArrayOutputStream()
        ZipOutputStream(byteArrayOutputStream).use { zipOut ->
            imagens.forEachIndexed { index, image ->
                try {
                    val entryName = "frame_$index.png"
                    zipOut.putNextEntry(ZipEntry(entryName))

                    ImageIO.write(image, "png", zipOut)
                    zipOut.closeEntry()

                    logger.debug("Imagem '$entryName' adicionada ao arquivo ZIP com sucesso.")
                } catch (e: Exception) {
                    logger.error("Erro ao adicionar imagem ao ZIP: ${e.message}", e)
                }
            }
        }

        logger.info("Arquivo ZIP criado com sucesso! Tamanho final: ${byteArrayOutputStream.size()} bytes")

        return byteArrayOutputStream.toByteArray()
    }
}