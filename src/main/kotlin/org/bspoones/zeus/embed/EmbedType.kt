package org.bspoones.zeus.embed

/**
 * Represents the type of AutoEmbed.
 *
 * This enum class defines the types of embedded messages supported:
 * - [CONTEXT]: An embedded message tailored for contextual information, such as command responses.
 * - [INFORMATION]: An embedded message containing general information.
 * - [FULL_CUSTOM]: A fully customized embedded message.
 *
 * @property CONTEXT An embedded message tailored for contextual information.
 * @property INFORMATION An embedded message containing general information.
 * @property FULL_CUSTOM A fully customized embedded message.
 * @author <a href="https://www.bspoones.com">BSpoones</a>
 */
enum class EmbedType {
    CONTEXT,
    INFORMATION,
    FULL_CUSTOM
}