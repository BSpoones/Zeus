package org.bspoones.zeus.component.modal.config

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle
import net.dv8tion.jda.internal.interactions.component.TextInputImpl
import org.bspoones.zeus.component.modal.AutoModalInput
import org.bspoones.zeus.util.text.parse
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class ConfigModalInput(
    var inputStyle: TextInputStyle = TextInputStyle.UNKNOWN,
    var id: String = "",
    var label: String = "",
    var placeholder: String? = null,
    var value: String? = null,
    var required: Boolean = true,
    var minLength: Int? = null,
    var maxLength: Int? = null
) {

    fun toAutoModalInput(vararg placeholders: Any): AutoModalInput {
        return AutoModalInput(
            inputStyle,
            id.parse(*placeholders),
            label.parse(*placeholders),
            placeholder.parse(*placeholders),
            value.parse(*placeholders),
            required,
            minLength ?: -1,
            maxLength ?: -1
        )
    }

    fun toModalInput(vararg placeholders: Any): TextInputImpl {
        return toAutoModalInput(*placeholders).toTextInput()
    }

}