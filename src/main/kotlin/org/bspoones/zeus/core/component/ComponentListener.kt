package org.bspoones.zeus.core.component

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.GenericSelectMenuInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.Component

/**
 * **ComponentListener**
 *
 * Responsible for all component execution
 * @author <a href="https://www.bspoones.com">BSpoones</a>
 */
object ComponentListener: ListenerAdapter() {

    /**
     * Button Interaction event
     *
     * Finds button unit in the map and executes it
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        val unit = ComponentRegistry.buttonMap[event.componentId] ?: return
        unit.invoke(event)
    }

    /**
     * Button Modal event
     *
     * Finds modal unit in the map and executes it
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    override fun onModalInteraction(event: ModalInteractionEvent) {
        val unit = ComponentRegistry.modalMap[event.modalId] ?: return
        unit.invoke(event)
    }

    /**
     * Select menu Interaction event
     *
     * Finds select menu unit in the map and executes it
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    override fun onGenericSelectMenuInteraction(event: GenericSelectMenuInteractionEvent<*,*>) {
        if (event.component.type == Component.Type.STRING_SELECT) {
            val unit = ComponentRegistry.stringSelectMap[event.componentId] ?: return
            unit.invoke(event)
        } else {
            val unit = ComponentRegistry.entitySelectMap[event.componentId] ?: return
            unit.invoke(event)
        }

    }
}