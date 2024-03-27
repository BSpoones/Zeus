package org.bspoones.zeus.component

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.GenericSelectMenuInteractionEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger

/**
 * **Component Registry**
 *
 * Responsible for registration of all components
 */
object ComponentRegistry {
    lateinit var api: JDA
    private var logger: Logger = getLogger("Zeus | Component handler")

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

    /**
     * Used to register buttons at startup, meaning a button of the given ID
     * will always work
     *
     * ```kotlin
     * registerButton("myButtonId") { event: ButtonInteractionEvent ->
     *      // Button logic here
     * }
     *```
     * @see org.bspoones.zeus.component.button.AutoButton
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    fun registerButton(id: String, button: (ButtonInteractionEvent) -> Unit) {
        buttonMap[id] = button
        logger.info("Button of ID: $id registered successfully")
    }

    /**
     * Used to register modals at startup, meaning a modal of the given ID
     * will always work
     *
     * ```kotlin
     * registerModal("myModalId") { event: ModalInteractionEvent ->
     *      // Modal logic here
     * }
     *```
     * @see org.bspoones.zeus.component.modal.AutoModal
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    fun registerModal(id: String, modal: (ModalInteractionEvent) -> Unit) {
        modalMap[id] = modal
        logger.info("Modal of ID: $id registered successfully")
    }

    /**
     * Used to register string select menus at startup, meaning a string select menu of the given ID
     * will always work
     *
     * ```kotlin
     * registerStringSelectMenu("myStringSelectMenuId") { event: GenericSelectMenuInteractionEvent<*, *> ->
     *      // String select menu logic here
     * }
     *```
     * @see org.bspoones.zeus.component.select.AutoSelectMenu
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    fun registerStringSelectMenu(id: String, stringSelect: (GenericSelectMenuInteractionEvent<*, *>) -> Unit) {
        stringSelectMap[id] = stringSelect
        logger.info("String select menu of ID: $id registered successfully")
    }

    /**
     * Used to register entity select menus at startup, meaning an entity select menu of the given ID
     * will always work
     *
     * ```kotlin
     * registerEntitySelectMenu("myEntitySelectMenuId") { event: GenericSelectMenuInteractionEvent<*, *> ->
     *      // Entity select menu logic here
     * }
     *```
     * @see org.bspoones.zeus.component.select.AutoSelectMenu
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    fun registerEntitySelectMenu(id: String, entitySelect: (GenericSelectMenuInteractionEvent<*, *>) -> Unit) {
        entitySelectMap[id] = entitySelect
        logger.info("Entity select menu of ID: $id registered successfully")
    }



}