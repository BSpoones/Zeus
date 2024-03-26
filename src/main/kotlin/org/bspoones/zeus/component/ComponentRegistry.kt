package org.bspoones.zeus.component

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.GenericSelectMenuInteractionEvent

/**
 * **Component Registry**
 *
 * Responsible for registration of all components
 */
object ComponentRegistry {
    lateinit var api: JDA

    val buttonMap: MutableMap<String, (ButtonInteractionEvent) -> Unit> = mutableMapOf()
    val modalMap: MutableMap<String, (ModalInteractionEvent) -> Unit> = mutableMapOf()

    val stringSelectMap: MutableMap<String, (GenericSelectMenuInteractionEvent<*, *>) -> Unit> = mutableMapOf()
    val entitySelectMap: MutableMap<String, (GenericSelectMenuInteractionEvent<*, *>) -> Unit> = mutableMapOf()

    /**
     * Set up the Component Registry
     *
     * This **should** be done within Zeus itself, so you don't have to worry!
     *
     * @param api [JDA] - Discord bot instance
     *
     * @see JDA
     * @see org.bspoones.zeus.component
     *
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    fun setup(api: JDA) {
        this.api = api

        this.api.addEventListener(
            ComponentListener
        )
    }
}