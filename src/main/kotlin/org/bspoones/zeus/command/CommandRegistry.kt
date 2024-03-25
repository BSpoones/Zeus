package org.bspoones.zeus.command

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import org.bspoones.zeus.command.handler.CommandTreeHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import kotlin.reflect.KClass

object CommandRegistry {
    private val logger: Logger = getLogger("Zeus | Command Handler")

    lateinit var api: JDA
    lateinit var globalMessagePrefix: String
    lateinit var prefixGuildMap: MutableMap<Long, String>
    lateinit var guilds: List<Long>


    val autoCompleteMap: MutableMap<String, Map<String, List<Any>>> = mutableMapOf()
    val customChoiceMap: MutableMap<String, () -> Unit> = mutableMapOf()

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

    fun setVariableChoice(id: String, block: () -> Unit) {
        customChoiceMap[id] = block
    }

    fun getVariableChoice(id: String): () -> Unit {
        return customChoiceMap[id] ?: throw NoSuchMethodException("Unable to find variable declaration for $id")
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
        return prefixGuildMap[guildId] ?: globalMessagePrefix
    }

    fun setup(
        api: JDA,
        globalMessagePrefix: String = "!",
        prefixGuildMap: MutableMap<Long, String> = mutableMapOf(),
        guilds: List<Long> = listOf()
    ) {
        this.api = api
        this.globalMessagePrefix = globalMessagePrefix
        this.prefixGuildMap = prefixGuildMap
        this.guilds = guilds
    }

}


