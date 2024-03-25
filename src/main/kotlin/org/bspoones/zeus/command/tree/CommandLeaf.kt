package org.bspoones.zeus.command.tree

import org.bspoones.zeus.command.enums.CommandType
import kotlin.reflect.KFunction

class CommandLeaf(
    val type: CommandType,
    val name: String,
    val function: KFunction<*>
)