package org.bspoones.zeus.extensions

import net.dv8tion.jda.api.entities.IMentionable
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.Role
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.entities.channel.Channel
import net.dv8tion.jda.api.interactions.commands.OptionType
import kotlin.reflect.KType
import kotlin.reflect.jvm.javaType

fun KType.optionType(): OptionType? = when (this.javaType) {
    String::class.java -> OptionType.STRING
    Int::class.java -> OptionType.INTEGER
    Boolean::class.java -> OptionType.BOOLEAN
    Double::class.java -> OptionType.NUMBER
    User::class.java -> OptionType.USER
    Channel::class.java -> OptionType.CHANNEL
    Role::class.java -> OptionType.ROLE
    IMentionable::class.java -> OptionType.MENTIONABLE
    Message.Attachment::class.java -> OptionType.ATTACHMENT
    else -> null
}