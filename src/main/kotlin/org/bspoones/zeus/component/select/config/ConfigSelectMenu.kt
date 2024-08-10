package org.bspoones.zeus.component.select.config

import net.dv8tion.jda.api.events.interaction.component.GenericSelectMenuInteractionEvent
import net.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu.SelectTarget
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu
import org.bspoones.zeus.component.select.AutoSelectMenu
import org.bspoones.zeus.component.select.config.enums.ConfigChannelType
import org.bspoones.zeus.component.select.config.enums.ConfigSelectMenuType
import org.bspoones.zeus.util.text.parse
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class ConfigSelectMenu(
    var selectMenuType: ConfigSelectMenuType = ConfigSelectMenuType.STRING,
    var customId: String = "",
    var placeholder: String? = null,
    var minValues: Int? = null,
    var maxValues: Int? = null,
    var disabled: Boolean = false,
    var selectTarget: MutableList<SelectTarget>? = null,
    var channelTypes: MutableList<ConfigChannelType>? = null,
    var options: MutableList<ConfigSelectOption>? = null
) {

    fun autoSelectMenu(
        vararg placeholders: Any,
        onSelect: ((GenericSelectMenuInteractionEvent<*, *>) -> Unit) = {}
    ): AutoSelectMenu {

        return when (selectMenuType) {
            ConfigSelectMenuType.STRING -> {
                AutoSelectMenu.StringOptionBuilder()
                    .setCustomId(customId.parse(*placeholders))
                    .setPlaceholder(placeholder.parse(*placeholders))
                    .setMinValues(minValues)
                    .setMaxValues(maxValues)
                    .setDisabled(disabled)
                    .setOptions(options?.map { it.toSelectOption(*placeholders) } ?: listOf())
                    .setOnSelect(onSelect)
                    .build()
            }

            ConfigSelectMenuType.ENTITY -> {
                AutoSelectMenu.EntityOptionBuilder()
                    .setCustomId(customId.parse(*placeholders))
                    .setPlaceholder(placeholder.parse(*placeholders))
                    .setMinValues(minValues)
                    .setMaxValues(maxValues)
                    .setDisabled(disabled)
                    .setSelectTarget(selectTarget ?: listOf())
                    .setChannelTypes(channelTypes?.map { it.toChannelType() } ?: listOf())
                    .setOnSelect(onSelect)
                    .build()
            }
        }
    }


    fun toSelectMenu(
        vararg placeholders: Any,
        onSelect: ((GenericSelectMenuInteractionEvent<*, *>) -> Unit) = {}
    ): SelectMenu {
        val auto = autoSelectMenu(*placeholders, onSelect = onSelect)

        return when (selectMenuType) {
            ConfigSelectMenuType.STRING -> auto.toStringMenu()
            ConfigSelectMenuType.ENTITY -> auto.toEntityMenu()
        }
    }


}