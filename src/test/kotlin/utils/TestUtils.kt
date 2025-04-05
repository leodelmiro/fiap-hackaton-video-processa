package utils

import com.leodelmiro.recebevideo.core.domain.Arquivo
import org.jcodec.api.SequenceEncoder
import org.jcodec.common.Codec
import org.jcodec.common.Format
import org.jcodec.common.io.NIOUtils
import org.jcodec.common.model.ColorSpace
import org.jcodec.common.model.Picture
import org.jcodec.common.model.Rational
import org.jcodec.scale.AWTUtil
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File

fun criaArquivo() = Arquivo(nome = "video", key = "video.mp4", autor = "test", descricao = "teste")

fun criaMp4File(
    nomeArquivo: String = "video.mp4",
    largura: Int = 1280,
    altura: Int = 720,
    numFrames: Int = 30,
    fps: Rational = Rational(30, 1),
    outputFormat: Format = Format.MOV,
    outputVideoCodec: Codec = Codec.H264,
    outputAudioCodec: Codec? = null
): File {
    return try {
        val file = File(nomeArquivo)
        val outputChannel = NIOUtils.writableChannel(file)

        val encoder = SequenceEncoder(outputChannel, fps, outputFormat, outputVideoCodec, outputAudioCodec)

        for (i in 0 until numFrames) {
            val image = BufferedImage(largura, altura, BufferedImage.TYPE_3BYTE_BGR)
            val g: Graphics2D = image.createGraphics().apply {
                color = Color.BLUE
                fillRect(0, 0, largura, altura)
                color = Color.WHITE
                drawString("Frame $i", largura / 2, altura / 2)
                dispose()
            }

            val picture: Picture = AWTUtil.fromBufferedImage(image, ColorSpace.RGB)
            encoder.encodeNativeFrame(picture)
        }

        encoder.finish()
        println("Arquivo MP4 criado com sucesso: ${file.absolutePath}")
        file
    } catch (e: Exception) {
        e.printStackTrace()
        throw RuntimeException("Falha ao criar arquivo MP4", e)
    }
}

fun criarImagem(largura: Int, altura: Int, cor: Color): BufferedImage {
    val imagem = BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB)
    val graphics = imagem.createGraphics()
    graphics.color = cor
    graphics.fillRect(0, 0, largura, altura)
    graphics.dispose()
    return imagem
}