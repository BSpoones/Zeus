package org.bspoones.zeus.command.annotations.choices

import net.dv8tion.jda.api.entities.channel.ChannelType

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class ChannelTypes(
    val channelTypes: Array<ChannelType>
)
