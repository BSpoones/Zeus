package org.bspoones.zeus.component.modal

import net.dv8tion.jda.api.interactions.components.text.TextInputStyle
import net.dv8tion.jda.internal.interactions.component.TextInputImpl

/**
 * **AutoModalInput**
 *
 * An easy way to create a Modal text input
 * @property inputStyle The style of the input field.
 * @property id The ID of the input field.
 * @property label The label text of the input field.
 * @property placeholder The placeholder text of the input field. Default is null.
 * @property value The default value of the input field. Default is null.
 * @property required Whether the input field is required. Default is true.
 * @property minLength The minimum length of the input field. Default is -1.
 * @property maxLength The maximum length of the input field. Default is -1.
 *
 * @author <a href="https://www.bspoones.com">BSpoones</a>
 */
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

    /**
     * Converts the AutoModalInput to a TextInputImpl.
     *
     * @return A new TextInputImpl instance created from the properties of the AutoModalInput.
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
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