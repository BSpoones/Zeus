package org.bspoones.chloris.command.register

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import org.bspoones.chloris.command.Command
import org.bspoones.chloris.command.handler.CommandTreeHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import kotlin.reflect.KClass

object CommandRegister {
    private var _api: JDA? = null

    var api: JDA
        get() = _api
            ?: throw ClassNotFoundException("Unable to locate JDA instance. Make sure you include setApi() before you register any commands!")
        set(value) {
            _api = value
        }
    var globalMessagePrefix: String = "!"
    var prefixGuildMap: MutableMap<Long, String> = mutableMapOf()
    var guilds: List<Long> = listOf()

    private val logger: Logger = getLogger("Chloris | Command Handler")

    val autoCompleteMap: MutableMap<String, Map<String, List<Any>>> = mutableMapOf()

    private val commandRegistry: MutableList<CommandData> = mutableListOf()

    fun registerCommands(commandClazzes: List<KClass<*>>, guildOnly: Boolean = false) {
        logger.info("Registering ${commandClazzes.size} ${if (guildOnly) "guild" else "global"} commands...")
        commandClazzes.forEach { clazz ->
            CommandTreeHandler.buildCommandTree(clazz).forEach { command ->
                logger.info("Registering ${command.name}")
                commandRegistry.add(command.setGuildOnly((command.isGuildOnly || guildOnly)))
            }
        }

        registerGlobalCommands()
        registerGuildCommands()

    }

    private fun registerGlobalCommands() {
        api.updateCommands().addCommands(commandRegistry.filter { !it.isGuildOnly }).queue()
    }

    private fun registerGuildCommands() {
        guilds.forEach { guildID ->
            val guild = api.getGuildById(guildID) ?: return@forEach
            guild.updateCommands().addCommands(commandRegistry.filter { it.isGuildOnly }).complete()
        }
    }


    fun getPrefix(guildId: Long): String {
        return this.prefixGuildMap[guildId] ?: globalMessagePrefix
    }

    fun setup(
        api: JDA,
        globalMessagePrefix: String = "!",
        prefixGuildMap: MutableMap<Long, String> = mutableMapOf(),
        guilds: List<Long> = listOf()
    ) {
        this.api = api
        this.api.addEventListener(Command())

        this.globalMessagePrefix = globalMessagePrefix
        this.prefixGuildMap = prefixGuildMap
        this.guilds = guilds
    }

}


