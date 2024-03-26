package org.bspoones.zeus.command.handler

import net.dv8tion.jda.api.interactions.commands.Command
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData
import org.bspoones.zeus.command.annotations.SlashCommandGroup
import org.bspoones.zeus.command.annotations.command.MessageCommand
import org.bspoones.zeus.command.annotations.command.SlashCommand
import org.bspoones.zeus.command.annotations.command.context.MessageContextCommand
import org.bspoones.zeus.command.annotations.command.context.UserContextCommand
import org.bspoones.zeus.command.enums.CommandType
import org.bspoones.zeus.command.tree.CommandForest
import java.lang.RuntimeException
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

/**
 * Responsible for building all command groups, commands, options, and choices
 *
 * @author <a href="https://www.bspoones.com">BSpoones</a>
 */
object CommandTreeHandler {


    /**
     * Adds a command clazz to the command tree
     *
     * @param clazz [KClass] - Command Object
     * @see org.bspoones.zeus.command.Command
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    fun buildCommandTree(clazz: KClass<*>): List<CommandData> {
        this.logger.info("Building command tree")
        return if (clazz.hasAnnotation<SlashCommandGroup>()) listOf(buildGroup(clazz)) else buildCommands(clazz)
    }

    /**
     * Adds a command group to the command tree
     *
     * @param clazz [KClass] - Command Object
     * @see org.bspoones.zeus.command.annotations.SlashCommandGroup
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    private fun buildGroup(clazz: KClass<*>): CommandData {
        val group = clazz.findAnnotation<SlashCommandGroup>()
            ?: throw RuntimeException("Parent class building when it shouldn't")
        this.logger.info("Building ${group.name}")

        val commandGroup = Commands.slash(group.name, group.description)
            .setDefaultPermissions(PermissionHandler.buildPermissions(clazz))
            .setNSFW(NsfwHandler.buildNsfw(clazz))
            .setGuildOnly(GuildOnlyHandler.buildGuildOnly(clazz))

        // Nested groups
        val subGroups: MutableList<SubcommandGroupData> = mutableListOf()
        clazz.nestedClasses
            .filter { it.hasAnnotation<SlashCommandGroup>() }
            .map { subClazz ->
                subGroups.add(buildSubGroup(subClazz, group.name))
            }
        if (subGroups.isNotEmpty()) {
            commandGroup.addSubcommandGroups(subGroups)
        }

        // Group commands
        val subCommands = buildSubCommands(clazz, group.name)
        if (subCommands.isNotEmpty()) {
            commandGroup.addSubcommands(subCommands)
        }

        return commandGroup
    }

    /**
     * Adds a command subgroup to the command tree
     *
     * @param clazz [KClass] - Command Object
     * @param parentName [String] - parent command name. (parent group)
     * @see org.bspoones.zeus.command.annotations.SlashCommandGroup
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    private fun buildSubGroup(clazz: KClass<*>, parentName: String = ""): SubcommandGroupData {
        val subGroupAnnotation = clazz.findAnnotation<SlashCommandGroup>()
            ?: throw RuntimeException("Failed to find sub group!")
        val subGroup = SubcommandGroupData(
            subGroupAnnotation.name,
            subGroupAnnotation.description
        )

        val subCommands = buildSubCommands(clazz, "${if (parentName.isNotBlank()) "$parentName " else ""}${subGroupAnnotation.name}")
        if (subCommands.isNotEmpty()) {
            subGroup.addSubcommands(subCommands)
        }

        return subGroup
    }

    /**
     * Adds a sub command to the command tree
     *
     * @param clazz [KClass] - Command Object
     * @param parentName [String] - parent command name. (parent group)
     * @see org.bspoones.zeus.command.annotations.command.SlashCommand
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    private fun buildSubCommands(clazz: KClass<*>, parentName: String = ""): List<SubcommandData> {
        val commands: List<SubcommandData> = clazz.declaredMemberFunctions
            .filter { it.hasAnnotation<SlashCommand>() }
            .flatMap { method ->
                val slashCommand = method.findAnnotation<SlashCommand>()?.let {
                    val commandName = "${if (parentName.isNotBlank()) "$parentName " else ""}${it.name}"
                    CommandForest.addLeaf(CommandType.SLASH, commandName, method)
                    SubcommandData(it.name, it.description)
                        .addOptions(OptionHandler.buildOptions(method, commandName))

                }

                listOfNotNull(slashCommand)
            }
        return commands
    }

    /**
     * Adds a clazz of commands to the command tree
     *
     * @param clazz [KClass] - Command Object
     * @param parentName [String] - parent command name. (parent group)
     * @see org.bspoones.zeus.command.annotations.command.SlashCommand
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    private fun buildCommands(clazz: KClass<*>, parentName: String = ""): List<CommandData> {
        val commands: MutableList<CommandData> = clazz.declaredMemberFunctions
            .filter { it.hasAnnotation<SlashCommand>() || it.hasAnnotation<MessageCommand>() || it.hasAnnotation<UserContextCommand>() || it.hasAnnotation<MessageContextCommand>() }
            .flatMap { method ->
                val slashCommand = method.findAnnotation<SlashCommand>()?.let {
                    val commandName = "${if (parentName.isNotBlank()) "$parentName " else ""}${it.name}"
                    CommandForest.addLeaf(CommandType.SLASH, commandName, method)
                    Commands.slash(it.name, it.description)
                        .addOptions(OptionHandler.buildOptions(method, commandName))
                        .setDefaultPermissions(PermissionHandler.buildPermissions(method))
                        .setNSFW(NsfwHandler.buildNsfw(method))
                        .setGuildOnly(GuildOnlyHandler.buildGuildOnly(method))
                }
                val messageCommand = method.findAnnotation<MessageCommand>()?.let {
                    CommandForest.addLeaf(CommandType.MESSAGE, it.name, method)
                    null // Message commands are handled internally
                }

                val userContextCommand= method.findAnnotation<UserContextCommand>()?.let {
                    CommandForest.addLeaf(CommandType.USER_CONTEXT, it.name, method)
                    Commands.context(Command.Type.USER, it.name)
                }
                val messageContextCommand= method.findAnnotation<MessageContextCommand>()?.let {
                    CommandForest.addLeaf(CommandType.MESSAGE_CONTEXT, it.name, method)
                    Commands.context(Command.Type.MESSAGE, it.name)
                        .setDefaultPermissions(PermissionHandler.buildPermissions(method))
                        .setNSFW(NsfwHandler.buildNsfw(method))
                        .setGuildOnly(GuildOnlyHandler.buildGuildOnly(method))
                }

                listOfNotNull(slashCommand, messageCommand, userContextCommand, messageContextCommand)
            }.toMutableList()

        // Command group
        clazz.nestedClasses.forEach {
            if (!it.hasAnnotation<SlashCommandGroup>()) return@forEach
            commands.add(buildGroup(it))
        }

        return commands
    }

}