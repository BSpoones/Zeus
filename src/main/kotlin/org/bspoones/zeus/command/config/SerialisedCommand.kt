package org.bspoones.zeus.command.config

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder
import org.bspoones.zeus.command.enums.CommandType
import org.bspoones.zeus.command.enums.toCommandType
import org.bspoones.zeus.embed.config.ConfigEmbed
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class SerialisedCommand(
    var name: String,
    var description: String,
    var commandTypes: MutableList<CommandType>,

    var permissions: MutableList<Permission>,
    var nsfw: Boolean,

    var passMember: Boolean,

    var message: String?,
    var embeds: MutableList<ConfigEmbed>,

    var replyEphemeral: Boolean = false,
) {
    constructor() : this("", "", mutableListOf(), mutableListOf(), false, false, null, mutableListOf())

    fun run(event: GenericCommandInteractionEvent) {
        if (event.guild == null) return
        if (event.member == null) return
        if (!commandTypes.contains(event.commandType.toCommandType())) return

        val message = MessageCreateBuilder()
            .setContent(message)
            .setEmbeds(embeds.map {
                if (passMember) {
                    it.embed(event.member!!)
                } else {
                    it.embed()
                }
            })
            .build()

        event.reply(message).setEphemeral(replyEphemeral).queue()
    }
}