package com.leodelmiro.recebevideo.core.usecase.impl

import com.leodelmiro.recebevideo.core.usecase.TransformaVideoEmImagensUseCase
import com.leodelmiro.recebevideo.dataprovider.UploadImagensS3Impl
import org.jcodec.api.FrameGrab
import org.jcodec.common.io.NIOUtils
import org.jcodec.scale.AWTUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import javax.imageio.ImageIO


class TransformaVideoEmImagensUseCaseImpl(private val uploadImagensS3Impl: UploadImagensS3Impl) :
    TransformaVideoEmImagensUseCase {
    private val logger: Logger = LoggerFactory.getLogger(TransformaVideoEmImagensUseCaseImpl::class.java)

    override fun executar(arquivo: File, arquivoNome: String): String {
        logger.debug("Iniciando transformação de video para imagens.")

        val grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(arquivo))
        logger.debug("Total frames: " + grab.videoTrack.meta.totalFrames)

        var picture = grab.nativeFrame
        var frameNumero = 0
        while (picture != null) {
            val image = AWTUtil.toBufferedImage(picture)
            val outputStream = ByteArrayOutputStream()
            ImageIO.write(image, "png", outputStream)

            uploadImagensS3Impl.executar(outputStream.toByteArray(), arquivoNome, frameNumero)
            frameNumero++
            picture = grab.nativeFrame
        }
        return "videos/${arquivoNome}/frames"
    }
}
