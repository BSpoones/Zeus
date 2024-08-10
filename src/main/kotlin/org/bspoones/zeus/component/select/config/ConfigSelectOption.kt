package org.bspoones.zeus.component.select.config

import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.interactions.components.selections.SelectOption
import org.bspoones.zeus.util.text.parse
import org.spongepowered.configurate.objectmapping.ConfigSerializable

// TODO -> Javadoc
@ConfigSerializable
data class ConfigSelectOption(
    var label: String = "",
    var value: String = "",
    var description: String? = null,
    var isDefault: Boolean = false,
    var emoji: String? = null
) {

    fun toSelectOption(vararg placeholders: Any): SelectOption {
        val emojiObj = if (emoji != null) Emoji.fromFormatted(emoji!!) else null

        return SelectOption.of(
            label.parse(*placeholders),
            value.parse(*placeholders)
        )
            .withDescription(description.parse(*placeholders))
            .withDefault(isDefault)
            .withEmoji(emojiObj)


    }


}