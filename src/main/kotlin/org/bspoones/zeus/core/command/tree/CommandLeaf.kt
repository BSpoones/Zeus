package org.bspoones.zeus.core.command.tree

import org.bspoones.zeus.core.command.enums.CommandType
import kotlin.reflect.KFunction

/**
 * Command Leaf
 *
 * Storage node for [CommandForest]
 */
class CommandLeaf(
    val type: CommandType,
    val name: String,
    val function: KFunction<*>
)