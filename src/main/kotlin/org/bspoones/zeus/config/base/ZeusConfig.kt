package org.bspoones.zeus.config.base

import net.dv8tion.jda.api.requests.GatewayIntent
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
class ZeusConfig {
    var token: String = ""

    var globalMessagePrefix: String = "!"

    var whitelistedGuildIds: MutableList<Long> = mutableListOf()

    var guildPrefixMap: MutableMap<Long, String> = mutableMapOf()

    var gatewayIntents: MutableList<GatewayIntent> = GatewayIntent.entries.toMutableList()

}