package org.bspoones.zeus.embed.config

import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class ConfigEmbedTimestamp(
    var time: Long? = null
)