package org.bspoones.zeus

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import org.bspoones.zeus.core.command.Command
import org.bspoones.zeus.core.command.CommandRegistry
import org.bspoones.zeus.core.component.ComponentRegistry
import org.bspoones.zeus.core.extras.Banner
import org.bspoones.zeus.core.message.MessageUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger

const val VERSION = "1.3"

/**
 * **Zeus**
 *
 * A multipurpose discord command handler
 *
 * @see <a href="https://github.com/BSpoones/Zeus">Zeus GitHub</a>
 *
 * @author <a href="https://www.bspoones.com">BSpoones</a>
 */
abstract class Zeus {
    private lateinit var _api: JDA
    private var isSetup: Boolean = false
    private val logger: Logger = getLogger("Zeus")
    val api: JDA
        get() = _api

    val globalMessagePrefix: String = "!"
    val whitelistGuilds: MutableList<Long> = mutableListOf()
    val guildPrefixMap: MutableMap<Long, String> = mutableMapOf()

    open fun getToken(): String = "" // TODO -> Base config

    open fun getCommands(): List<Command> = listOf()

    open fun getIntents(): List<GatewayIntent> = GatewayIntent.entries

    fun isSetup(): Boolean = isSetup

    fun start(logging: Boolean = true) {
        if (logging) Banner.logBanner()
        this._api = JDABuilder.createDefault(
            getToken(),
            getIntents()
        ).build()

        this._api.awaitReady()

        CommandRegistry.setup(
            this.api,
            this.globalMessagePrefix,
            this.guildPrefixMap,
            this.whitelistGuilds
        )
        ComponentRegistry.setup(this.api)
        MessageUtils.setup(this.api)

        setupListeners()

        if (logging) logger.info("${api.selfUser.name} ready on ${api.guilds.size} servers")
    }


    /**
     * Adds all required Listeners
     */
    private fun setupListeners() {
        this.api.addEventListener(Command())
    }
}