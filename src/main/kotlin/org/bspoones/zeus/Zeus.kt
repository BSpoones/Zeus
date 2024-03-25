package org.bspoones.zeus

import net.dv8tion.jda.api.JDA
import org.bspoones.zeus.command.Command
import org.bspoones.zeus.command.CommandRegistry
import org.bspoones.zeus.component.ComponentRegistry
import kotlin.reflect.KClass

object Zeus {
    private lateinit var api: JDA
    private lateinit var globalMessagePrefix: String
    private lateinit var prefixGuildMap: MutableMap<Long, String>
    private lateinit var guilds: List<Long>

    fun setup(
        api: JDA,
        globalMessagePrefix: String = "!",
        prefixGuildMap: MutableMap<Long, String> = mutableMapOf(),
        guilds: List<Long> = listOf()
    ) {
        this.api
        this.globalMessagePrefix
        this.prefixGuildMap
        this.guilds

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