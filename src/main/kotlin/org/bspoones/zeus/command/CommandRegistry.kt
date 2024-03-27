package org.bspoones.zeus.command

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import org.bspoones.zeus.command.CommandRegistry.prefixGuildMap
import org.bspoones.zeus.command.handler.CommandTreeHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import kotlin.reflect.KClass

/**
 * **Command Registry**
 *
 * Responsible for all group, command, option, choice registration
 *
 * @author <a href="https://www.bspoones.com">BSpoones</a>
 */
object CommandRegistry {
    private val logger: Logger = getLogger("Zeus | Command Handler")

    lateinit var api: JDA
    lateinit var globalMessagePrefix: String
    lateinit var prefixGuildMap: MutableMap<Long, String>
    lateinit var guilds: List<Long>

    val autoCompleteMap: MutableMap<String, Map<String, List<Any>>> = mutableMapOf()

    private val customChoiceMap: MutableMap<String, () -> Collection<Any>> = mutableMapOf()
    private var commandRegistry: MutableList<CommandData> = mutableListOf()


    /**
     * Register all commands within a class Object
     *
     * ```kotlin
     * object ExampleCommand: Command() {
     *
     *     @SlashCommand("name","description")
     *     fun onNameCommand(...) {...}
     * }
     *
     * CommandRegistry.registerCommands(
     *     ExampleCommand::class,
     *     AnotherCommand::class,
     *     ThirdCommand::class,
     *     guildOnly = true // Optional
     * )
     * ```
     *
     * @param commands [KClass] - Command Objects (see above)
     * @param guildOnly [Boolean] - Register all commands to a list of guilds. Default = false
     * @see org.bspoones.zeus.command.Command
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    fun registerCommands(vararg commands: KClass<*>, guildOnly: Boolean = false) {
        // Reset the command registry every time
        commandRegistry = mutableListOf()

        logger.info("Registering ${commands.size} ${if (guildOnly) "guild" else "global"} commands...")
        commands.forEach { clazz ->
            CommandTreeHandler.buildCommandTree(clazz).forEach { command ->
                logger.info("Registering ${command.name}")
                commandRegistry.add(command.setGuildOnly((command.isGuildOnly || guildOnly)))
            }
        }

        registerGlobalCommands()
        registerGuildCommands()
    }

    /**
     * Set variable choice unit
     *
     * The provided method will be called to get a list of command choices whenever
     * the commands are re-registered
     *
     * @param id [String] - Custom choice ID. This will be checked against the Method (Command) option ID
     * @param block [Unit] - Unit that returns a list of the required type
     * @see org.bspoones.zeus.command.annotations.choices.variable
     * @see org.bspoones.zeus.command.handler.OptionHandler.buildOptions
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    fun setVariableChoice(id: String, block: () -> Collection<Any>) {
        customChoiceMap[id] = block
    }

    /**
     * Retrieve the variable choice unit form the choice map
     *
     * @param id [String] - Custom choice ID. This should be set **before the commands are registered**
     *
     * @return Collection<[Any]> - Collection of choices
     * @see org.bspoones.zeus.command.annotations.choices.variable
     * @see org.bspoones.zeus.command.handler.OptionHandler.buildOptions
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    fun getVariableChoice(id: String): () -> Collection<Any> {
        return customChoiceMap[id] ?: throw NoSuchMethodException("Unable to find variable declaration for $id")
    }


    /**
     * Register all global commands to the API
     *
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    private fun registerGlobalCommands() {
        api.updateCommands().addCommands(commandRegistry.filter { !it.isGuildOnly }).queue()
    }

    /**
     * Register all global commands to the all guilds set in [guilds]
     *
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    private fun registerGuildCommands() {
        guilds.forEach { guildID ->
            val guild = api.getGuildById(guildID) ?: return@forEach
            guild.updateCommands().addCommands(commandRegistry.filter { it.isGuildOnly }).complete()
        }
    }


    /**
     * Retrieves the guild prefix set in [prefixGuildMap], if any
     *
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    fun getPrefix(guildId: Long): String {
        return prefixGuildMap[guildId] ?: globalMessagePrefix
    }

    /**
     * Set up the Command Registry
     *
     * This **should** be done within Zeus itself, so you don't have to worry!
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
        guilds: List<Long> = listOf()
    ) {
        this.api = api
        this.globalMessagePrefix = globalMessagePrefix
        this.prefixGuildMap = prefixGuildMap
        this.guilds = guilds
    }

}


