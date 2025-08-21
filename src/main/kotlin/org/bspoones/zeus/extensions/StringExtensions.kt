package org.bspoones.zeus.extensions

import java.awt.Color
import java.net.URLEncoder



fun String.toColor(): Color {
    val hex = this.removePrefix("#")
    return when (hex.length) {
        6 -> Color(
            hex.substring(0, 2).toInt(16),
            hex.substring(2, 4).toInt(16),
            hex.substring(4, 6).toInt(16)
        )
        8 -> Color(
            hex.substring(0, 2).toInt(16),
            hex.substring(2, 4).toInt(16),
            hex.substring(4, 6).toInt(16),
            hex.substring(6, 8).toInt(16)
        )
        else -> throw IllegalArgumentException("Invalid hex color format")
    }
}

fun String.URLEncode() = URLEncoder.encode(this, "UTF-8")