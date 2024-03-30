package org.bspoones.zeus

import net.dv8tion.jda.api.JDA
import org.bspoones.zeus.command.Command
import org.bspoones.zeus.command.CommandRegistry
import org.bspoones.zeus.component.ComponentRegistry
import org.bspoones.zeus.extras.Banner
import org.bspoones.zeus.message.MessageUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger

const val VERSION = "1.2"

/**
 * **Zeus**
 *
 * A multipurpose discord command handler
 *
 * @see <a href="https://github.com/BSpoones/Zeus">Zeus GitHub</a>
 *
 * @author <a href="https://www.bspoones.com">BSpoones</a>
 */
object Zeus {
    private lateinit var api: JDA
    private lateinit var globalMessagePrefix: String
    private lateinit var prefixGuildMap: MutableMap<Long, String>
    private lateinit var guilds: List<Long>

    var logger: Logger = getLogger("Zeus")
    var isSetup = false


    /**
     * **Zeus Setup**
     *
     * Run this to initialise Zeus
     *
     * @param api [JDA] - Discord bot instance
     * @param globalMessagePrefix [String] - Global message command prefix
     * @param prefixGuildMap MutableMap<[Long],[String]> - Map of guilds to custom prefixes
     * @param guilds List<[Long]> - List of guilds to set guild only commands for
     *
     * @see JDA
     * @see org.bspoones.zeus.command.annotations.GuildOnly
     * @see org.bspoones.zeus.command.handler.GuildOnlyHandler
     *
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    fun setup(
        api: JDA,
        globalMessagePrefix: String = "!",
        prefixGuildMap: MutableMap<Long, String> = mutableMapOf(),
        guilds: List<Long> = listOf(),
        logBanner: Boolean = true
    ) {
        logger.info("Starting Zeus v$VERSION")
        if (logBanner) {
            Banner.logBanner()
        }

        this.api = api
        this.globalMessagePrefix = globalMessagePrefix
        this.prefixGuildMap = prefixGuildMap
        this.guilds = guilds

        CommandRegistry.setup(
            this.api,
            this.globalMessagePrefix,
            this.prefixGuildMap,
            this.guilds
        )
        ComponentRegistry.setup(this.api)
        MessageUtils.setup(this.api)

        setupListeners()

        this.isSetup = true
    }


    /**
     * Adds all required Listeners
     */
    private fun setupListeners() {
        this.api.addEventListener(Command())
    }
}