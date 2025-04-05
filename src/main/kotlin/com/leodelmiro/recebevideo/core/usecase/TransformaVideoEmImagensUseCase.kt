package com.leodelmiro.recebevideo.core.usecase

import java.awt.image.BufferedImage
import java.io.File

interface TransformaVideoEmImagensUseCase {
    fun executar(arquivo: File): List<BufferedImage>
}