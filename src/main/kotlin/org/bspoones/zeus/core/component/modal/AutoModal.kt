package org.bspoones.zeus.core.component.modal

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent
import net.dv8tion.jda.api.interactions.components.ActionRow
import net.dv8tion.jda.internal.interactions.modal.ModalImpl
import org.bspoones.zeus.core.component.ComponentRegistry

/**
 * **AutoModal**
 *
 * An easy way to make Discord Modal Components!
 *
 * ```kotlin
 * @SlashCommand("name", "description")
 * fun onCommand(
 *     event: SlashCommandInteractionEvent
 * ) {
 *     val modal = AutoModal.Builder()
 *         .setCustomId("example")
 *         .setTitle("Example Title")
 *         .setInputs(listOf(AutoModalInput())
 *         .setOnSubmit { event: ModalInteractionEvent ->
 *             // Submit behaviour here
 *         }
 *         .build()
 *
 *     event.replyModal(modal.toModal()).queue()
 * }
 * ```
 * @see org.bspoones.zeus.command.annotations.command.SlashCommand
 * @see [AutoModalInput]
 * @see [ModalInteractionEvent]
 *
 * @author <a href="https://www.bspoones.com">BSpoones</a>
 */
class AutoModal private constructor(
    val customId: String,
    val title: String,
    val inputs: List<AutoModalInput>,
    val onSubmit: ((ModalInteractionEvent) -> Unit)?
) {
    /**
     * AutoModal Builder
     *
     * ```kotlin
     * val modal = AutoModal.Builder()
     *     .setCustomId("example")
     *     .setTitle("Example Title")
     *     .setInputs(listOf(AutoModalInput())
     *     .setOnSubmit { event: ModalInteractionEvent ->
     *         // Submit behaviour here
     *     }
     *     .build()
     * ```
     *
     * @see [AutoModalInput]
     * @see [ModalInteractionEvent]
     *
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    class Builder {
        private var customId: String = ""
        private var title: String = ""
        private var inputs: List<AutoModalInput> = listOf()
        private var onSubmit: ((ModalInteractionEvent) -> Unit)? = null

        fun setCustomId(customId: String) = apply { this.customId = customId }
        fun setTitle(title: String) = apply { this.title = title }
        fun setInputs(inputs: List<AutoModalInput>) = apply { this.inputs = inputs }
        fun setOnSubmit(onSubmit: ((ModalInteractionEvent) -> Unit)?) = apply { this.onSubmit = onSubmit }

        fun build(): AutoModal {
            if (onSubmit != null) {
                ComponentRegistry.modalMap[customId] = onSubmit!!
            }


            return AutoModal(customId, title, inputs, onSubmit)
        }
    }

    /**
     * Creates a discord modal
     *
     * @return [ModalImpl] - Modal instance
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    fun toModal(): ModalImpl {
        return ModalImpl(
            customId,
            title,
            inputs.map{ ActionRow.of(it.toTextInput())}
        )
    }

    companion object {
        /**
         * Creates a new AutoButton instance with the specified parameters.
         *
         * @param customId The custom ID of the modal. Default is an empty string.
         * @param title The title of the modal. Default is an empty string.
         * @param inputs The list of inputs for the modal. Default is an empty list.
         * @param onSubmit The function to be executed when the modal is submitted. Default is null.
         * @return A new AutoModal instance configured with the specified parameters.
         *
         * @author <a href="https://www.bspoones.com">BSpoones</a>
         */
        fun of(
            customId: String = "",
            title: String = "",
            inputs: List<AutoModalInput> = listOf(),
            onSubmit: ((ModalInteractionEvent) -> Unit)? = null,
        ): AutoModal {
            return Builder()
                .setCustomId(customId)
                .setTitle(title)
                .setInputs(inputs)
                .setOnSubmit(onSubmit)
                .build()
        }
    }
}
