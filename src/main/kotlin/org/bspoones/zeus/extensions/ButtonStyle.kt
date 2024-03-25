package org.bspoones.zeus.extensions

import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle
import net.dv8tion.jda.internal.interactions.component.ButtonImpl

fun ButtonStyle.toButton(
    customId: String,
    label: String,
    disabled: Boolean = false,
    emoji: Emoji? = null
): ButtonImpl {
    return ButtonImpl(customId, label, this, disabled, emoji)
}