package org.bspoones.zeus.embed

import net.dv8tion.jda.api.entities.User

class AutoEmbedFooter private constructor(){

    lateinit var text: String
    lateinit var iconUrl: String

    constructor(
        text: String,
        iconUrl: String
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