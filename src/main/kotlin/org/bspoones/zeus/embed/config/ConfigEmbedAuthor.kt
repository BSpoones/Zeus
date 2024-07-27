package org.bspoones.zeus.embed.config

import org.bspoones.zeus.embed.AutoEmbedAuthor
import org.bspoones.zeus.util.text.parse
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class ConfigEmbedAuthor(
    var name: String,
    var iconUrl: String?,
    var url: String?
) {
    constructor() : this("", null, null)

    fun autoEmbedAuthor(vararg placeholders: Any): AutoEmbedAuthor? {
        if (name == "") return null
        return AutoEmbedAuthor(
            name.parse(*placeholders),
            url.parse(*placeholders),
            iconUrl.parse(*placeholders)
        )
    }
}
