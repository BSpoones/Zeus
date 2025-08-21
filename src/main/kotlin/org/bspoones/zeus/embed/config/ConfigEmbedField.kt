package org.bspoones.zeus.embed.config

import org.bspoones.zeus.embed.AutoEmbedField
import org.bspoones.zeus.util.text.parse
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class ConfigEmbedField(
    var name: String = "",
    var value: String = "",
    var inline: Boolean = false
) {
    fun autoEmbedField(vararg placeholders: Any) = AutoEmbedField(
        name.parse(*placeholders),
        value.parse(*placeholders),
        inline
    )
}