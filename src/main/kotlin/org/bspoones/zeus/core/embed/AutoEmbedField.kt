package org.bspoones.zeus.core.embed

/**
 * Represents a field in an embed.

 * @property name The name of the field.
 * @property value The value of the field.
 * @property inline Whether the field should be displayed inline. Default is false.
 * @constructor Creates an AutoEmbedField with the specified properties.
 * @author <a href="https://www.bspoones.com">BSpoones</a>
 */
class AutoEmbedField(
    val name: String,
    val value: String,
    val inline: Boolean
) {
}