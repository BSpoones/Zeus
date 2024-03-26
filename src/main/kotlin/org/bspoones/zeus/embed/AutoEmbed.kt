package org.bspoones.zeus.embed

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder
import net.dv8tion.jda.api.utils.messages.MessageCreateData
import java.awt.Color
import java.time.LocalTime
import java.time.temporal.TemporalAccessor

/**
 * **AutoEmbed**
 *
 * An easy way to make Discord Embeds!
 *
 * ```kotlin
 * @SlashCommand("name", "description")
 * fun onCommand(
 *     event: SlashCommandInteractionEvent
 * ) {
 *     // Note: Combinations of these should be used
 *     val embed = AutoEmbed.Builder()
 *         .setEmbedType(EmbedType.FULL_CUSTOM)
 *         .setTitle("Title")
 *         .setTitleUrl("https://www.bspoones.com/"
 *         .setDescription("Description"
 *         .setFields(listOf(AutoEmbedField())
 *         .setColor(Color.RED)
 *         .setAuthor(AutoEmbedAuthor)
 *         .setFooter(AutoEmbedFooter)
 *         .setThumbnailUrl("IMAGE LINK")
 *         .setImageUrl("IMAGE LINK")
 *         .setTimeStamp(LocalTime.Now()) // Default
 *         .setSenderUser(event.user) // Not required
 *         .setSenderMember(event.member) // Not required
 *
 *         .setCustomId("example")
 *         .setTitle("Example Title")
 *         .setInputs(listOf(AutoModalInput())
 *         .setOnSubmit { event: ModalInteractionEvent ->
 *             // Submit behaviour here
 *         }
 *         .build()
 *     val = MessageCreateBuilder()
 *         .setEmbeds(embed.toEmbed())
 *         .addActionRow(button.toButton())
 *         .build()
 *
 *     event.reply(message).queue()
 *
 *     // OR
 *     event.reply(embed.toMessageCreateData())
 * }
 * ```
 * @see org.bspoones.zeus.command.annotations.command.SlashCommand
 * @see [MessageEmbed]
 * @see [Color]
 * @see [EmbedType]
 * @see [MessageCreateBuilder]
 *
 * @author <a href="https://www.bspoones.com">BSpoones</a>
 */
class AutoEmbed(
    val embedType: EmbedType,
    val user: User?,
    val member: Member?,
    val title: String?,
    val titleUrl: String?,
    val description: String?,
    val fields: List<AutoEmbedField>?,
    val color: Color?,
    val author: AutoEmbedAuthor?,
    val footer: AutoEmbedFooter?,
    val thumbnailUrl: String?,
    val imageUrl: String?,
    val timestamp: TemporalAccessor? = LocalTime.now()
) {

    /**
     * AutoEmbed Builder
     *
     * ```kotlin
     * val embed = AutoEmbed.Builder()
     *         .setEmbedType(EmbedType.FULL_CUSTOM)
     *         .setTitle("Title")
     *         .setTitleUrl("https://www.bspoones.com/"
     *         .setDescription("Description"
     *         .setFields(listOf(AutoEmbedField())
     *         .setColor(Color.RED)
     *         .setAuthor(AutoEmbedAuthor)
     *         .setFooter(AutoEmbedFooter)
     *         .setThumbnailUrl("IMAGE LINK")
     *         .setImageUrl("IMAGE LINK")
     *         .setTimeStamp(LocalTime.Now()) // Default
     *         .setSenderUser(event.user) // Not required
     *         .setSenderMember(event.member) // Not required
     *         .build()
     * ```
     *
     * @see [MessageEmbed]
     * @see [Color]
     * @see [EmbedType]
     *
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
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

        fun setEmbedType(embedType: EmbedType) = apply { this.embedType = embedType }
        fun setEmbedType(embedType: String) = apply {
            this.embedType =
                EmbedType.values().find { it.name == embedType.uppercase() } ?: throw IllegalArgumentException(
                    "Invalid embed type. Please choose from ${
                        EmbedType.values().joinToString("\n- ")
                    }"
                )
        }

        fun setTitle(title: String?) = apply { this.title = title }
        fun setTitleUrl(titleUrl: String?) = apply { this.titleUrl = titleUrl }
        fun setDescription(description: String?) = apply { this.description = description }
        fun setFields(fields: List<AutoEmbedField>?) = apply { this.fields = fields }
        fun setColor(color: Color?) = apply { this.color = color }
        fun setAuthor(author: AutoEmbedAuthor?) = apply { this.author = author }
        fun setFooter(footer: AutoEmbedFooter?) = apply { this.footer = footer }
        fun setThumbnailUrl(thumbnailUrl: String?) = apply { this.thumbnailUrl = thumbnailUrl }
        fun setImageUrl(imageUrl: String?) = apply { this.imageUrl = imageUrl }
        fun setTimestamp(timestamp: TemporalAccessor?) = apply { this.timestamp = timestamp }
        fun setSenderUser(sender: User?) = apply { this.senderUser = sender }
        fun setSenderMember(sender: Member?) = apply { this.senderMember = sender }

        fun build(): AutoEmbed {
            if (embedType == EmbedType.CONTEXT) {
                color = color ?: senderMember?.color
                footer =
                    footer ?: senderUser?.let { AutoEmbedFooter(it) } ?: senderMember?.let { AutoEmbedFooter(it.user) }
            }

            return AutoEmbed(
                embedType, senderUser, senderMember, title, titleUrl, description,
                fields, color, author, footer, thumbnailUrl, imageUrl, timestamp
            )
        }
    }


    /**
     * Converts the AutoEmbed instance to a MessageEmbed.
     *
     * @return A MessageEmbed instance configured with the properties of the AutoEmbed.
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    fun toEmbed(): MessageEmbed {
        val embed = EmbedBuilder()
            .setTitle(title)
            .setUrl(titleUrl)
            .setDescription(description)
            .setColor(color)
            .setThumbnail(thumbnailUrl)
            .setImage(imageUrl)
            .setTimestamp(timestamp)

        author?.let { embed.setAuthor(it.name, it.url, it.iconUrl) }
        footer?.let { embed.setFooter(it.text, it.iconUrl) }

        fields?.forEach { field ->
            embed.addField(field.name, field.value, field.inline)
        }

        return embed.build()
    }

    /**
     * Converts the AutoEmbed instance to a MessageCreateBuilder.
     *
     * @return A MessageCreateBuilder instance configured with the embed of the AutoEmbed.
     * @see [toEmbed]
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    fun toMessageCreateBuilder() : MessageCreateBuilder {
        return MessageCreateBuilder()
            .setEmbeds(toEmbed())
    }

    /**
     * Converts the AutoEmbed instance to a MessageCreateData.
     *
     * @return A MessageCreateData instance configured with the embed of the AutoEmbed.
     * @see [toMessageCreateBuilder]
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    fun toMessageCreateData() : MessageCreateData {
        return toMessageCreateBuilder().build()
    }

}