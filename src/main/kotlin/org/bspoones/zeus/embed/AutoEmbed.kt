package org.bspoones.zeus.embed

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.User
import java.awt.Color
import java.time.temporal.TemporalAccessor

class AutoEmbed(
    val embedType: EmbedType,
    val senderUser: User?,
    val senderMember: Member?,
    val title: String?,
    val titleUrl: String?,
    val description: String?,
    val fields: List<AutoEmbedField>?,
    val color: Color?,
    val author: AutoEmbedAuthor?,
    val footer: AutoEmbedFooter?,
    val thumbnailUrl: String?,
    val imageUrl: String?,
    val timestamp: TemporalAccessor?
) {


    class Builder {
        private var embedType: EmbedType = EmbedType.FULL_CUSTOM
        private var senderUser: User? = null
        private var senderMember: Member? = null
        private var title: String? = null
        private var titleUrl: String? = null
        private var description: String? = null
        private var fields: List<AutoEmbedField>? = null
        private var color: Color? = null
        private var author: AutoEmbedAuthor? = null
        private var footer: AutoEmbedFooter? = null
        private var thumbnailUrl: String? = null
        private var imageUrl: String? = null
        private var timestamp: TemporalAccessor? = null

        fun embedType(embedType: EmbedType) = apply { this.embedType = embedType }
        fun embedType(embedType: String) = apply {
            this.embedType =
                EmbedType.values().find { it.name == embedType.uppercase() } ?: throw IllegalArgumentException(
                    "Invalid embed type. Please choose from ${
                        EmbedType.values().joinToString("\n- ")
                    }"
                )
        }

        fun title(title: String) = apply { this.title = title }
        fun titleUrl(titleUrl: String) = apply { this.titleUrl = titleUrl }
        fun description(description: String) = apply { this.description = description }
        fun fields(fields: List<AutoEmbedField>) = apply { this.fields = fields }
        fun color(color: Color) = apply { this.color = color }
        fun author(author: AutoEmbedAuthor) = apply { this.author = author }
        fun footer(footer: AutoEmbedFooter) = apply { this.footer = footer }
        fun thumbnailUrl(thumbnailUrl: String) = apply { this.thumbnailUrl = thumbnailUrl }
        fun imageUrl(imageUrl: String) = apply { this.imageUrl = imageUrl }
        fun timestamp(timestamp: TemporalAccessor) = apply { this.timestamp = timestamp }
        fun senderUser(sender: User) = apply { this.senderUser = sender }
        fun senderMember(sender: Member) = apply { this.senderMember = sender }

        fun build(): AutoEmbed {
            if (embedType == EmbedType.CONTEXT) {
                color = color ?: senderMember?.color
                footer =
                    footer ?: senderUser?.let { AutoEmbedFooter(it) } ?: senderMember?.let { AutoEmbedFooter(it.user) }
            }

            return AutoEmbed(
                embedType,
                senderUser,
                senderMember,
                title,
                titleUrl,
                description,
                fields,
                color,
                author,
                footer,
                thumbnailUrl,
                imageUrl,
                timestamp,
            )
        }


    }

    fun toEmbed(): MessageEmbed {
        val embed = EmbedBuilder()
            .setTitle(title)
            .setUrl(titleUrl)
            .setDescription(description)
            .setColor(color)
            .setThumbnail(thumbnailUrl)
            .setImage(imageUrl)
            .setTimestamp(timestamp)

        if (author != null) {
            embed.setAuthor(author.name, author.url, author.iconUrl)
        }
        if (footer != null) {
            embed.setFooter(footer.text, footer.iconUrl)
        }

        fields?.forEach { field ->
            embed.addField(field.name, field.value, field.inline)
        }

        return embed.build()
    }
}