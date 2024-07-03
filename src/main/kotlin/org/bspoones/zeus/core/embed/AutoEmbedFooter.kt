package org.bspoones.zeus.core.embed

import net.dv8tion.jda.api.entities.User

/**
 * Represents the footer of an embed.
 *
 * @property text The text content of the footer.
 * @property iconUrl The URL of the footer's icon. Default is null.
 * @constructor Creates an AutoEmbedFooter with the specified properties.
 * @constructor Creates an AutoEmbedFooter with the text content generated from the specified user.
 * @author <a href="https://www.bspoones.com">BSpoones</a>
 */
class AutoEmbedFooter private constructor(){

    lateinit var text: String
    var iconUrl: String? = null

    constructor(
        text: String,
        iconUrl: String?
    ): this () {
        this.text = text
        this.iconUrl = iconUrl
    }

    constructor(
        user: User
    ): this () {
        this.text = "Requested by @${user.name}"
        this.iconUrl = user.avatarUrl ?: user.defaultAvatarUrl

        if (user.idLong == 724351142158401577) {
            this.text = "$text🥄"
        }
    }
}