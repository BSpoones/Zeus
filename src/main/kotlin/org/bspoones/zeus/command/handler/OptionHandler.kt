package org.bspoones.zeus.command.handler

import net.dv8tion.jda.api.entities.IMentionable
import net.dv8tion.jda.api.entities.Message.Attachment
import net.dv8tion.jda.api.entities.Role
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.entities.channel.Channel
import net.dv8tion.jda.api.interactions.commands.OptionMapping
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import org.bspoones.zeus.command.annotations.CommandOption
import org.bspoones.zeus.command.annotations.choices.ChannelTypes
import org.bspoones.zeus.command.CommandRegistry
import org.bspoones.zeus.extensions.optionType
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.javaType

val USER_PING_REGEX = "<@!?(\\d+)>".toRegex()
val ROLE_PING_REGEX = "<@&!?(\\d+)>".toRegex()
val CHANNEL_PING_REGEX = "<#!?(\\d+)>".toRegex()
val NUMBER_ONLY_REGEX = "\\d+".toRegex()

object OptionHandler {
    fun buildOptions(method: KFunction<*>, commandName: String): List<OptionData> {
        val options = mutableListOf<OptionData>()

        method.parameters.forEach { parameter ->
            val commandOption = parameter.findAnnotation<CommandOption>() ?: return@forEach
            val optionType = parameter.type.optionType()
                ?: throw IllegalArgumentException("${parameter.name} has an invalid parameter type: ${parameter.type}")

            val optionData =
                OptionData(optionType, commandOption.name, commandOption.description, commandOption.isRequired, commandOption.autoComplete)

            if (commandOption.autoComplete) {
                val optionChoice = CommandRegistry.autoCompleteMap.getOrDefault(commandName, mapOf()).toMutableMap()
                optionChoice[commandOption.name] = ChoiceHandler.getChoices(parameter)
                CommandRegistry.autoCompleteMap[commandName] = optionChoice
            } else {
                optionData.addChoices(ChoiceHandler.buildChoices(parameter))
            }

            val channelTypesAnnotation = parameter.findAnnotation<ChannelTypes>()
            if (channelTypesAnnotation != null) {
                optionData.setChannelTypes(channelTypesAnnotation.channelTypes.toList())
            }

            options.add(optionData)
        }
        return options
    }


    fun getMessageOption(arg: String, parameterType: KType, attachment: Attachment? = null): Any? {
        return when (parameterType) {
            String::class.java -> arg
            Int::class.java -> arg.toInt()
            Boolean::class.java -> arg.toBoolean()
            Double::class.java -> arg.toDouble()
            User::class.java -> {
                if (!USER_PING_REGEX.matches(arg)) null
                else {
                    val id = NUMBER_ONLY_REGEX.find(arg)?.value ?: return null
                    CommandRegistry.api.getUserById(id.toLong())
                }
            }
            Channel::class.java -> {
                if (!CHANNEL_PING_REGEX.matches(arg)) null
                else {
                    val id = NUMBER_ONLY_REGEX.find(arg)?.value ?: return null
                    CommandRegistry.api.getChannelById((parameterType as Channel)::class.java, id.toLong())
                }
            }
            Role::class.java -> {
                if (!ROLE_PING_REGEX.matches(arg)) null
                else {
                    val id = NUMBER_ONLY_REGEX.find(arg)?.value ?: return null
                    CommandRegistry.api.getRoleById(id.toLong())
                }
            }
            IMentionable::class.java -> {
                val roleMatch = ROLE_PING_REGEX.matches(arg)
                val userMatch = USER_PING_REGEX.matches(arg)
                if (!roleMatch && !userMatch) null
                else {
                    val id = NUMBER_ONLY_REGEX.find(arg)?.value ?: return null
                    if (roleMatch) CommandRegistry.api.getRoleById(id.toLong())
                    else  CommandRegistry.api.getUserById(id.toLong())
                }
            }
            Attachment::class.java -> {
                if (attachment == null) return null
                attachment
            }
            else -> null
        }
    }
}