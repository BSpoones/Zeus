package org.bspoones.zeus.component.select.config.enums

import net.dv8tion.jda.api.entities.channel.ChannelType


enum class ConfigChannelType {
    TEXT,
    PRIVATE,
    VOICE,
    GROUP,
    CATEGORY,
    NEWS,
    STAGE,
    GUILD_NEWS_THREAD,
    GUILD_PUBLIC_THREAD,
    GUILD_PRIVATE_THREAD,
    FORUM,
    MEDIA,
    UNKNOWN;

    fun toChannelType(): ChannelType {
        return when (this) {
            TEXT -> ChannelType.TEXT
            PRIVATE -> ChannelType.PRIVATE
            VOICE -> ChannelType.VOICE
            GROUP -> ChannelType.GROUP
            CATEGORY -> ChannelType.CATEGORY
            NEWS -> ChannelType.NEWS
            STAGE -> ChannelType.STAGE
            GUILD_NEWS_THREAD -> ChannelType.GUILD_NEWS_THREAD
            GUILD_PUBLIC_THREAD -> ChannelType.GUILD_PUBLIC_THREAD
            GUILD_PRIVATE_THREAD -> ChannelType.GUILD_PRIVATE_THREAD
            FORUM -> ChannelType.FORUM
            MEDIA -> ChannelType.MEDIA
            UNKNOWN -> ChannelType.UNKNOWN
        }
    }


}