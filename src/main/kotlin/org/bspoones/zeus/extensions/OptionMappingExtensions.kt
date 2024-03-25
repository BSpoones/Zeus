package org.bspoones.zeus.extensions

import net.dv8tion.jda.api.interactions.commands.OptionMapping
import net.dv8tion.jda.api.interactions.commands.OptionType
import kotlin.reflect.KType

fun OptionMapping.getOptionValue(parameterType: KType): Any? = when (parameterType.optionType()) {
    OptionType.STRING -> asString
    OptionType.INTEGER -> asInt
    OptionType.BOOLEAN -> asBoolean
    OptionType.NUMBER -> asDouble
    OptionType.USER -> asUser
    OptionType.CHANNEL -> asChannel
    OptionType.ROLE -> asRole
    OptionType.MENTIONABLE -> asMentionable
    OptionType.ATTACHMENT -> asAttachment
    else -> null
}