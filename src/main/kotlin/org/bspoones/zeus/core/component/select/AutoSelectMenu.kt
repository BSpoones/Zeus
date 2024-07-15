package org.bspoones.zeus.core.component.select

import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.events.interaction.component.GenericSelectMenuInteractionEvent
import net.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu
import net.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu.SelectTarget
import net.dv8tion.jda.api.interactions.components.selections.SelectOption
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu
import org.bspoones.zeus.core.component.ComponentRegistry

/**
 * **AutoSelectMenu**
 *
 * An easy way to make Discord Select Menu Components!
 *
 * ```kotlin
 * @SlashCommand("name", "description")
 * fun onCommand(
 *     event: SlashCommandInteractionEvent
 * ) {
 *     val stringSelectMenu = StringOptionBuilder()
 *         .setCustomId("example")
 *         .setPlaceholder("placeholder")
 *         .setMinValues(1)
 *         .setMaxValues(10)
 *         .setDisabled(true)
 *         .setOptions(listOf(SelectOption.of("name","value"))
 *         .setOnSelect { event: GenericSelectMenuInteractionEvent
 *             // Select logic here
 *         }
 *         .build()
 *
 *     val entitySelectMenu =  EntityOptionBuilder()
 *         .setCustomId("example")
 *         .setPlaceholder("placeholder")
 *         .setMinValues(1)
 *         .setMaxValues(10)
 *         .setDisabled(disabled)
 *         .setSelectTarget(listOf(SelectTarget.CHANNEL)
 *         .setChannelTypes(listOf(ChannelType.TEXT)) // Not needed if select target is not CHANNEL
 *         .setOnSelect { event: GenericSelectMenuInteractionEvent
 *             // Select logic here
 *         }
 *         .build()
 *
 *     val = MessageCreateBuilder()
 *         .setContent("Example message")
 *         .addActionRow(stringSelectMenu.toStringMenu())
 *         .addActionRow(entitySelectMenu.toEntityMenu())
 *         .build()
 *
 *     event.reply(message).queue()
 * }
 * ```
 * @see org.bspoones.zeus.core.command.annotations.command.SlashCommand
 * @see [StringSelectMenu]
 * @see [EntitySelectMenu]
 * @see [SelectTarget]
 * @see [GenericSelectMenuInteractionEvent]
 * @see [ChannelType]
 *
 * @author <a href="https://www.bspoones.com">BSpoones</a>
 */
class AutoSelectMenu(
    private val customId: String,
    private val placeholder: String? = null,
    private val minValues: Int = -1,
    private val maxValues: Int = -1,
    private val disabled: Boolean = false,
    private var selectTarget: List<SelectTarget> = listOf(),
    private var channelTypes: List<ChannelType> = listOf(),
    private val options: List<SelectOption> = listOf(),
    private val onSelect: ((GenericSelectMenuInteractionEvent<*, *>) -> Unit)? = null
) {

    /**
     * String Option Builder
     *
     * ```kotlin
     * val stringSelectMenu = StringOptionBuilder()
     *     .setCustomId("example")
     *     .setPlaceholder("placeholder")
     *     .setMinValues(1)
     *     .setMaxValues(10)
     *     .setDisabled(true)
     *     .setOptions(listOf(SelectOption.of("name","value"))
     *     .setOnSelect { event: GenericSelectMenuInteractionEvent
     *         // Select logic here
     *     }
     *     .build()
     * ```
     *
     * @see [SelectOption]
     * @see [GenericSelectMenuInteractionEvent]
     *
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    class StringOptionBuilder {
        private var customId: String = ""
        private var placeholder: String? = null
        private var minValues: Int = 1
        private var maxValues: Int = 1
        private var disabled: Boolean = false
        private var options: List<SelectOption> = listOf()
        private var onSelect: ((GenericSelectMenuInteractionEvent<*, *>) -> Unit)? = null

        fun setCustomId(customId: String) = apply { this.customId = customId }
        fun setPlaceholder(placeholder: String) = apply { this.placeholder = placeholder }
        fun setMinValues(minValues: Int) = apply { this.minValues = minValues }
        fun setMaxValues(maxValues: Int) = apply { this.maxValues = maxValues }
        fun setDisabled(disabled: Boolean) = apply { this.disabled = disabled }
        fun setOptions(options: List<SelectOption>) = apply { this.options = options }
        fun setOnSelect(onSelect: (GenericSelectMenuInteractionEvent<*, *>) -> Unit) = apply { this.onSelect = onSelect }

        fun build(): AutoSelectMenu {
            if (onSelect != null) {
                ComponentRegistry.stringSelectMap[customId] = onSelect!!
            }

            return AutoSelectMenu(
                customId = customId,
                placeholder = placeholder,
                minValues = minValues,
                maxValues = maxValues,
                disabled = disabled,
                options = options,
                onSelect = onSelect
            )
        }
    }

    /**
     * Entity Option Builder
     *
     * ```kotlin
     * val entitySelectMenu =  EntityOptionBuilder()
     *     .setCustomId("example")
     *     .setPlaceholder("placeholder")
     *     .setMinValues(1)
     *     .setMaxValues(10)
     *     .setDisabled(disabled)
     *     .setSelectTarget(listOf(SelectTarget.CHANNEL)
     *     .setChannelTypes(listOf(ChannelType.TEXT)) // Not needed if select target is not CHANNEL
     *     .setOnSelect { event: GenericSelectMenuInteractionEvent
     *         // Select logic here
     *     }
     *     .build()
     * ```
     *
     * @see [SelectTarget]
     * @see [ChannelType]
     * @see [GenericSelectMenuInteractionEvent]
     *
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    class EntityOptionBuilder() {
        private var customId: String = ""
        private var placeholder: String? = null
        private var minValues: Int = 1
        private var maxValues: Int = 1
        private var disabled: Boolean = false
        private var selectTarget: List<SelectTarget> = listOf()
        private var channelTypes: List<ChannelType> = listOf()
        private var onSelect: ((GenericSelectMenuInteractionEvent<*, *>) -> Unit)? = null

        fun setCustomId(customId: String) = apply { this.customId = customId }
        fun setPlaceholder(placeholder: String) = apply { this.placeholder = placeholder }
        fun setMinValues(minValues: Int) = apply { this.minValues = minValues }
        fun setMaxValues(maxValues: Int) = apply { this.maxValues = maxValues }
        fun setDisabled(disabled: Boolean) = apply { this.disabled = disabled }
        fun setSelectTarget(selectTarget: SelectTarget) = apply { this.selectTarget = listOf(selectTarget) }
        fun setSelectTarget(selectTarget: List<SelectTarget>) = apply { this.selectTarget = selectTarget }
        fun setChannelTypes(channelTypes: List<ChannelType>) = apply { this.channelTypes = channelTypes }
        fun setOnSelect(onSelect: (GenericSelectMenuInteractionEvent<*, *>) -> Unit) = apply { this.onSelect = onSelect }

        fun build(): AutoSelectMenu {
            if (onSelect != null) {
                ComponentRegistry.stringSelectMap[customId] = onSelect!!
            }

            return AutoSelectMenu(
                customId = customId,
                placeholder = placeholder,
                minValues = minValues,
                maxValues = maxValues,
                disabled = disabled,
                selectTarget = selectTarget,
                channelTypes = channelTypes,
                onSelect = onSelect
            )
        }
    }


    /**
     * Converts the AutoSelectMenu instance to a StringSelectMenu.
     *
     * @return A StringSelectMenu instance configured with the properties of the AutoSelectMenu.
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    fun toStringMenu(): StringSelectMenu {
        return StringSelectMenu.create(customId)
            .setPlaceholder(placeholder)
            .setMinValues(minValues)
            .setMaxValues(maxValues)
            .setDisabled(disabled)
            .setDefaultOptions(options)
            .build()
    }

    /**
     * Converts the AutoSelectMenu instance to an EntitySelectMenu.
     *
     * @return An EntitySelectMenu instance configured with the properties of the AutoSelectMenu.
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    fun toEntityMenu(): EntitySelectMenu {
        return EntitySelectMenu.create(customId, selectTarget)
            .setPlaceholder(placeholder)
            .setMinValues(minValues)
            .setMaxValues(maxValues)
            .setDisabled(disabled)
            .setChannelTypes(channelTypes)
            .build()
    }

}