package org.bspoones.zeus.component

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.GenericSelectMenuInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.Component

object ComponentListener: ListenerAdapter() {

    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        val unit = ComponentRegistry.buttonMap[event.componentId] ?: return
        unit.invoke(event)
    }

    override fun onModalInteraction(event: ModalInteractionEvent) {
        val unit = ComponentRegistry.modalMap[event.modalId] ?: return
        unit.invoke(event)
    }

    override fun onGenericSelectMenuInteraction(event: GenericSelectMenuInteractionEvent<*,*>) {
        println("SELECT MENU INTERACTION")
        if (event.component.type == Component.Type.STRING_SELECT) {
            val unit = ComponentRegistry.stringSelectMap[event.componentId] ?: return
            unit.invoke(event)
        } else {
            val unit = ComponentRegistry.entitySelectMap[event.componentId] ?: return
            unit.invoke(event)
        }

    }
}