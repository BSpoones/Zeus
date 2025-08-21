package org.bspoones.zeus.component.button.config

import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle
import net.dv8tion.jda.internal.interactions.component.ButtonImpl
import org.bspoones.zeus.component.button.AutoButton
import org.bspoones.zeus.util.text.parse
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class ConfigButton(
    var buttonStyle: ButtonStyle,
    var customId: String,
    var label: String,
    var disabled: Boolean,
    var emoji: String?
) {
    constructor() : this(ButtonStyle.PRIMARY, "", "", false, null)

    fun autoButton(vararg placeholders: Any, onClick: ((ButtonInteractionEvent) -> Unit) = {}): AutoButton {

        val emojiObj = if (emoji != null) Emoji.fromFormatted(emoji!!) else null

        return AutoButton.Builder()
            .setButtonStyle(buttonStyle)
            .setCustomId(customId.parse(*placeholders))
            .setLabel(label.parse(*placeholders))
            .setDisabled(disabled)
            .setEmoji(emojiObj)
            .setOnClick(onClick)
            .build()
    }

    fun toButton(vararg placeholders: Any, onClick: ((ButtonInteractionEvent) -> Unit) = {}): ButtonImpl {
        return autoButton(*placeholders, onClick = onClick).toButton()
    }
}