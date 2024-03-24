package org.bspoones.chloris.command.register

import org.bspoones.chloris.command.enums.CommandType
import kotlin.reflect.KFunction

class CommandLeaf(
    val type: CommandType,
    val name: String,
    val function: KFunction<*>
)