package org.bspoones.zeus.config.files

import net.dv8tion.jda.api.requests.GatewayIntent
import org.bspoones.zeus.config.annotations.ConfigDirectory
import org.bspoones.zeus.config.base.ActionableConfig
import org.bspoones.zeus.logging.getZeusLogger
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import java.nio.file.WatchEvent
import kotlin.system.exitProcess

/**
 * Main Zeus Config file
 *
 * This file contains all setup configuration
 */
@ConfigSerializable
@ConfigDirectory("zeus")
internal class ZeusConfig : ActionableConfig() {
    var token: String = ""

    var globalMessagePrefix: String = "!"

    var whitelistedGuildIds: MutableList<Long> = mutableListOf()

    var guildPrefixMap: MutableMap<Long, String> = mutableMapOf()

    var gatewayIntents: MutableList<GatewayIntent> = GatewayIntent.entries.toMutableList()

    // Any edits to this file should require a restart
    override fun onChange(event: WatchEvent<*>) {
        val logger = getZeusLogger("Zeus Config")

        logger.warn("Zeus Config has been changed! Restarting in:")
        for (i in 5 downTo 1) {
            println(i)
            Thread.sleep(1_000)
        }
        exitProcess(1)
    }
}