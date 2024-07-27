package org.bspoones.zeus.embed.config

import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.User
import org.bspoones.zeus.embed.AutoEmbed
import org.bspoones.zeus.embed.EmbedType
import org.bspoones.zeus.extensions.toColor
import org.bspoones.zeus.util.text.parse
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class ConfigEmbed(
    var type: EmbedType = EmbedType.FULL_CUSTOM,
    var title: String? = null,
    var description: MutableList<String>? = null,
    var color: String? = null,

    var titleUrl: String? = null,
    var thumbnailUrl: String? = null,
    var imageUrl: String? = null,

    var fields: MutableList<ConfigEmbedField>? = null,

    var timestamp: Boolean = false,

    var footer: ConfigEmbedFooter? = null,
    var author: ConfigEmbedAuthor? = null
) {
    fun embed(vararg placeholders: Any): MessageEmbed {
        return autoEmbed(null, null, placeholders).toEmbed()
    }

    fun embed(member: Member, vararg placeholders: Any) = autoEmbed(null, member, placeholders).toEmbed()

    fun autoEmbed(user: User? = null, member: Member? = null, vararg placeholders: Any): AutoEmbed {
        return AutoEmbed.Builder()
            .setEmbedType(type)
            .setTitle(title.parse(*placeholders))
            .setDescription(description?.joinToString("\n").parse(*placeholders))
            .setColor(color?.toColor())
            .setTitleUrl(titleUrl.parse(*placeholders))
            .setThumbnailUrl((thumbnailUrl.parse(*placeholders)))
            .setImageUrl(imageUrl.parse(*placeholders))
            .setFields(
                fields?.map { it.autoEmbedField(*placeholders) }
            )
            .setTimestamp(timestamp)
            .setFooter(footer?.autoEmbedFooter(*placeholders))
            .setAuthor(author?.autoEmbedAuthor(*placeholders))
            .setSenderMember(member)
            .setSenderUser(user)
            .build()
    }
}