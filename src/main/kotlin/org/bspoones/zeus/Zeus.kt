package org.bspoones.zeus

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import org.bspoones.zeus.config.files.MongoConfig
import org.bspoones.zeus.config.files.ZeusConfig
import org.bspoones.zeus.config.getConfig
import org.bspoones.zeus.config.initConfig
import org.bspoones.zeus.core.command.Command
import org.bspoones.zeus.core.command.CommandRegistry
import org.bspoones.zeus.core.component.ComponentRegistry
import org.bspoones.zeus.core.extras.Messages
import org.bspoones.zeus.core.message.MessageUtils
import org.bspoones.zeus.logging.SHOULD_LOG
import org.bspoones.zeus.logging.ZeusLogger
import org.bspoones.zeus.logging.getZeusLogger
import org.bspoones.zeus.storage.MongoConnection
import kotlin.reflect.KClass
import kotlin.system.exitProcess

const val VERSION = "1.3"
const val NAME = "ZEUS"

/**
 * **Zeus**
 *
 * A multipurpose discord command handler
 *
 * @see <a href="https://github.com/BSpoones/Zeus">Zeus GitHub</a>
 *
 * @author <a href="https://www.bspoones.com">BSpoones</a>
 */
abstract class Zeus(private val guildOnly: Boolean = false) {
    private lateinit var _api: JDA
    private var isSetup: Boolean = false
    private val logger: ZeusLogger = getZeusLogger("Core")
    val api: JDA
        get() = _api

    open fun initConfig() {}

    open fun initEntities() {}

    abstract fun getCommands(): List<KClass<*>>

    fun isSetup(): Boolean = isSetup

    fun start(logging: Boolean = true) {
        SHOULD_LOG = logging
        if (logging) Messages.logBanner()

        initConfig(
            ZeusConfig::class,
            MongoConfig::class
        )
        val config = getConfig<ZeusConfig>()

        // Config classed as incomplete if token is not given
        if (config.token == "") {
            Messages.logConfigErrorMessage()
            exitProcess(0)
        }

        this._api = JDABuilder.createDefault(
            config.token,
            config.gatewayIntents
        ).build()
        this._api.awaitReady()

        initConfig()

        MongoConnection.setup()

        initEntities()

        CommandRegistry.setup(this.api)
        registerCommands()

        ComponentRegistry.setup(this.api)
        MessageUtils.setup(this.api)

        setupListeners()

        logger.info("${api.selfUser.name} ready on ${api.guilds.size} servers")
        isSetup = true
        ZeusInstance.instance = this
    }

    fun registerCommands() {
        CommandRegistry.registerCommands(
            *getCommands().toTypedArray(),
            guildOnly = guildOnly
        )
    }

    /**
     * Adds all required Listeners
     */
    private fun setupListeners() {
        this.api.addEventListener(Command())
    }
}

object ZeusInstance {
    var instance: Zeus? = null
}
