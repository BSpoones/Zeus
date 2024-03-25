package org.bspoones.zeus.component.select

import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.events.interaction.component.GenericSelectMenuInteractionEvent
import net.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu
import net.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu.SelectTarget
import net.dv8tion.jda.api.interactions.components.selections.SelectOption
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu
import org.bspoones.zeus.component.ComponentRegistry

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
    class StringOptionBuilder {
        private var customId: String = ""
        private var placeholder: String? = null
        private var minValues: Int = 1
        private var maxValues: Int = 1
        private var disabled: Boolean = false
        private var options: List<SelectOption> = listOf()
        private var onSelect: ((GenericSelectMenuInteractionEvent<*, *>) -> Unit)? = null

        fun customId(customId: String) = apply { this.customId = customId }
        fun placeholder(placeholder: String) = apply { this.placeholder = placeholder }
        fun minValues(minValues: Int) = apply { this.minValues = minValues }
        fun maxValues(maxValues: Int) = apply { this.maxValues = maxValues }
        fun disabled(disabled: Boolean) = apply { this.disabled = disabled }
        fun options(options: List<SelectOption>) = apply { this.options = options }
        fun onSelect(onSelect: (GenericSelectMenuInteractionEvent<*, *>) -> Unit) = apply { this.onSelect = onSelect }

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


    class EntityOptionBuilder() {
        private var customId: String = ""
        private var placeholder: String? = null
        private var minValues: Int = 1
        private var maxValues: Int = 1
        private var disabled: Boolean = false
        private var selectTarget: List<SelectTarget> = listOf()
        private var channelTypes: List<ChannelType> = listOf()
        private var onSelect: ((GenericSelectMenuInteractionEvent<*, *>) -> Unit)? = null

        fun customId(customId: String) = apply { this.customId = customId }
        fun placeholder(placeholder: String) = apply { this.placeholder = placeholder }
        fun minValues(minValues: Int) = apply { this.minValues = minValues }
        fun maxValues(maxValues: Int) = apply { this.maxValues = maxValues }
        fun disabled(disabled: Boolean) = apply { this.disabled = disabled }
        fun selectTarget(selectTarget: SelectTarget) = apply { this.selectTarget = listOf(selectTarget) }
        fun selectTarget(selectTarget: List<SelectTarget>) = apply { this.selectTarget = selectTarget }
        fun channelTypes(channelTypes: List<ChannelType>) = apply { this.channelTypes = channelTypes }
        fun onSelect(onSelect: (GenericSelectMenuInteractionEvent<*, *>) -> Unit) = apply { this.onSelect = onSelect }

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


    fun toStringMenu(): StringSelectMenu {
        return StringSelectMenu.create(customId)
            .setPlaceholder(placeholder)
            .setMinValues(minValues)
            .setMaxValues(maxValues)
            .setDisabled(disabled)
            .setDefaultOptions(options)
            .build()
    }

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