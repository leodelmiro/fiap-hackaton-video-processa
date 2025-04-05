package com.leodelmiro.recebevideo.core.usecase

import java.awt.image.BufferedImage

interface RealizaZipImagensUseCase {
    fun executar(imagens: List<BufferedImage>): ByteArray
}