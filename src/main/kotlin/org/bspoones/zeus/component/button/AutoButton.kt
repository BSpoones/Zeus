package org.bspoones.zeus.component.button

import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle
import net.dv8tion.jda.internal.interactions.component.ButtonImpl
import org.bspoones.zeus.component.ComponentRegistry
import org.bspoones.zeus.extensions.toButton

class AutoButton private constructor(
    val buttonStyle: ButtonStyle,
    val customId: String,
    val label: String,
    val disabled: Boolean,
    val emoji: Emoji?,
    val onClick: ((ButtonInteractionEvent) -> Unit)?
) {
    class Builder {
        private var buttonStyle: ButtonStyle = ButtonStyle.PRIMARY
        private var customId: String = ""
        private var label: String = ""
        private var disabled: Boolean = false
        private var emoji: Emoji? = null
        private var onClick: ((ButtonInteractionEvent) -> Unit)? = null

        fun buttonStyle(buttonStyle: ButtonStyle) = apply { this.buttonStyle = buttonStyle }
        fun customId(customId: String) = apply { this.customId = customId }
        fun label(label: String) = apply { this.label = label }
        fun disabled(disabled: Boolean) = apply { this.disabled = disabled }
        fun emoji(emoji: Emoji?) = apply { this.emoji = emoji }
        fun onClick(onClick: ((ButtonInteractionEvent) -> Unit)?) = apply { this.onClick = onClick }

        fun build(): AutoButton {
            if (onClick != null) {
                ComponentRegistry.buttonMap[customId] = onClick!!
            }

            return AutoButton(buttonStyle, customId, label, disabled, emoji, onClick)
        }
    }

    fun toButton(): ButtonImpl {
        return this.buttonStyle.toButton(
            customId, label, disabled, emoji
        )
    }

    companion object {
        fun of(
            buttonStyle: ButtonStyle = ButtonStyle.PRIMARY,
            customId: String = "",
            label: String = "",
            disabled: Boolean = false,
            emoji: Emoji? = null,
            onClick: ((ButtonInteractionEvent) -> Unit)? = null
        ): AutoButton {
            return Builder()
                .buttonStyle(buttonStyle)
                .customId(customId)
                .label(label)
                .disabled(disabled)
                .emoji(emoji)
                .onClick(onClick)
                .build()
        }
    }
}
