package org.bspoones.zeus.message

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.utils.messages.MessageCreateData
import java.lang.Exception

object MessageUtils {

    lateinit var api: JDA

    fun setup(api: JDA) {
        this.api = api
    }

    fun getTextChannelById(id: Long) : TextChannel? {
        return api.getTextChannelById(id)
    }

    fun sendUserMessage(userID: Long, message: MessageCreateData) :Boolean {
        val user = api.getUserById(userID) ?: return false
        return sendUserMessage(user, message)
    }

    fun sendUserMessage(user: User, message: MessageCreateData): Boolean {
        return try {
            user.openPrivateChannel().flatMap { channel ->
                channel.sendMessage(message)
            }.queue()
            true
        } catch (e: Exception) {
            false
        }
    }

}