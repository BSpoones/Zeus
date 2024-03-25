package org.bspoones.zeus.component.modal

import net.dv8tion.jda.api.interactions.components.text.TextInputStyle
import net.dv8tion.jda.internal.interactions.component.TextInputImpl

class AutoModalInput(
    private val inputStyle: TextInputStyle,
    private val id: String,
    private val label: String,
    private val placeholder: String? = null,
    private val value: String? = null,
    private val required: Boolean = true,
    private val minLength: Int = -1,
    private val maxLength: Int = -1
) {

    fun toTextInput(): TextInputImpl {
        return TextInputImpl(
            id,
            inputStyle,
            label,
            minLength,
            maxLength,
            required,
            value,
            placeholder
        )
    }

}