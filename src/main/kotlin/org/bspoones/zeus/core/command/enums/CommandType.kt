package org.bspoones.zeus.core.command.enums

/**
 * Command Type Enum
 * 
 * Used to help locate commands on the command tree
 * 
 * @see org.bspoones.zeus.core.command.tree
 * @author <a href="https://www.bspoones.com">BSpoones</a>
 */
internal enum class CommandType(val typeName: String) {
    SLASH("Slash Command"),
    MESSAGE("Message Command"),
    USER_CONTEXT("User Context Command"),
    MESSAGE_CONTEXT("Message Context Command"),
}