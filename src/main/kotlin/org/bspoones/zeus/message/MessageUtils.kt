package org.bspoones.zeus.message

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.utils.messages.MessageCreateData
import java.lang.Exception

/**
 * Utility class for message-related operations.
 *
 *
 * @property api Discord bot instance.
 * @see [JDA]
 * @author <a href="https://www.bspoones.com">BSpoones</a>
 */
object MessageUtils {
    lateinit var api: JDA

    /**
     * Set up the Message Utils
     *
     * This **should** be done within Zeus itself, so you don't have to worry!
     *
     * @param api [JDA] - Discord bot instance
     * @see JDA
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    fun setup(api: JDA) {
        this.api = api
    }

    /**
     * Get a text channel from an ID
     *
     * @param id [Long] Channel ID
     * @return [TextChannel], if any
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    fun getTextChannelById(id: Long) : TextChannel? {
        return api.getTextChannelById(id)
    }

    /**
     * Sends a DM to a user from its ID
     *
     * @param userID The ID of the user to send the message to.
     * @param message The message to send.
     * @return true if the message was sent successfully, false otherwise.
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    fun sendUserMessage(userID: Long, message: MessageCreateData) :Boolean {
        val user = api.getUserById(userID) ?: return false
        return sendUserMessage(user, message)
    }

    /**
     * Sends a DM to a user from its ID
     *
     * @param user [User] The user to send the message to.
     * @param message The message to send.
     * @return true if the message was sent successfully, false otherwise.
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
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