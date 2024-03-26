package org.bspoones.zeus.embed

/**
 * Represents an embed author.
 *
 * @property name The name of the author.
 * @property url The URL of the author's profile. Default is null.
 * @property iconUrl The URL of the author's icon. Default is null.
 * @constructor Creates an AutoEmbedAuthor with the specified properties.
 * @author <a href="https://www.bspoones.com">BSpoones</a>
 */
class AutoEmbedAuthor(
    val name: String,
    val url: String,
    val iconUrl: String
) {
}