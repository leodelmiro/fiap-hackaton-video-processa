package com.leodelmiro.recebevideo.core.usecase.impl

import com.leodelmiro.recebevideo.core.usecase.TransformaVideoEmImagensUseCase
import org.jcodec.api.FrameGrab
import org.jcodec.common.io.NIOUtils
import org.jcodec.scale.AWTUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.image.BufferedImage
import java.io.File


class TransformaVideoEmImagensUseCaseImpl :
    TransformaVideoEmImagensUseCase {
    private val logger: Logger = LoggerFactory.getLogger(TransformaVideoEmImagensUseCaseImpl::class.java)

    override fun executar(arquivo: File): MutableList<BufferedImage> {
        logger.debug("Iniciando transformação de video para imagens.")

        val imagens: MutableList<BufferedImage> = mutableListOf()
        val grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(arquivo))
        logger.debug("Total frames: " + grab.videoTrack.meta.totalFrames)

        var picture = grab.nativeFrame
        while (picture != null) {
            val image = AWTUtil.toBufferedImage(picture)

            imagens.add(image)
            picture = grab.nativeFrame
        }
        return imagens
    }
}
