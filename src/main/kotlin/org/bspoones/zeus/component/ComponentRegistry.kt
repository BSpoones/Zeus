package org.bspoones.zeus.component

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.GenericSelectMenuInteractionEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object ComponentRegistry {

    private val logger: Logger = LoggerFactory.getLogger("Zeus | Component Handler")
    lateinit var api: JDA

    val buttonMap: MutableMap<String, (ButtonInteractionEvent) -> Unit> = mutableMapOf()
    val modalMap: MutableMap<String, (ModalInteractionEvent) -> Unit> = mutableMapOf()

    val stringSelectMap: MutableMap<String, (GenericSelectMenuInteractionEvent<*, *>) -> Unit> = mutableMapOf()
    val entitySelectMap: MutableMap<String, (GenericSelectMenuInteractionEvent<*, *>) -> Unit> = mutableMapOf()

    fun setup(api: JDA) {
        this.api = api

        this.api.addEventListener(
            ComponentListener
        )
    }
}