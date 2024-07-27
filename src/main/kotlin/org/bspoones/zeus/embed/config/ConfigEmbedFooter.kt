package org.bspoones.zeus.embed.config

import org.bspoones.zeus.embed.AutoEmbedFooter
import org.bspoones.zeus.util.text.parse
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class ConfigEmbedFooter(
    var text: String,
    var iconUrl: String
) {
    constructor() : this("", "")

    fun autoEmbedFooter(vararg placeholders: Any): AutoEmbedFooter? {
        if (text == "" || iconUrl == "") return null
        return AutoEmbedFooter(
            parse(text, *placeholders),
            parse(iconUrl, *placeholders)
        )
    }
}