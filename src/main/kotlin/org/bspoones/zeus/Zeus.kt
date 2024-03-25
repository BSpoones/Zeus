package org.bspoones.zeus

import net.dv8tion.jda.api.JDA
import org.bspoones.zeus.command.Command
import org.bspoones.zeus.command.CommandRegistry
import org.bspoones.zeus.component.ComponentRegistry
import kotlin.reflect.KClass

class Zeus(
    private val api: JDA,
    private val globalMessagePrefix: String = "!",
    private val prefixGuildMap: MutableMap<Long, String> = mutableMapOf(),
    private val guilds: List<Long> = listOf()
) {

    init {
        CommandRegistry.setup(
            this.api,
            this.globalMessagePrefix,
            this.prefixGuildMap,
            this.guilds
        )
        ComponentRegistry.setup(this.api)

        setupListeners()
    }

    private fun setupListeners() {
        this.api.addEventListener(Command())
    }
}