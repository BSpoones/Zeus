package org.bspoones.zeus.component.modal

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent
import net.dv8tion.jda.api.interactions.components.ActionRow
import net.dv8tion.jda.internal.interactions.modal.ModalImpl
import org.bspoones.zeus.component.ComponentRegistry

class AutoModal private constructor(
    val customId: String,
    val title: String,
    val inputs: List<AutoModalInput>,
    val onSubmit: ((ModalInteractionEvent) -> Unit)?
) {
    class Builder {
        private var customId: String = ""
        private var title: String = ""
        private var inputs: List<AutoModalInput> = listOf()
        private var onSubmit: ((ModalInteractionEvent) -> Unit)? = null

        fun customId(customId: String) = apply { this.customId = customId }
        fun title(title: String) = apply { this.title = title }
        fun inputs(inputs: List<AutoModalInput>) = apply { this.inputs = inputs }
        fun onSubmit(onSubmit: ((ModalInteractionEvent) -> Unit)?) = apply { this.onSubmit = onSubmit }

        fun build(): AutoModal {
            if (onSubmit != null) {
                ComponentRegistry.modalMap[customId] = onSubmit!!
            }


            return AutoModal(customId, title, inputs, onSubmit)
        }
    }

    fun toModal(): ModalImpl {
        return ModalImpl(
            customId,
            title,
            inputs.map{ ActionRow.of(it.toTextInput())}
        )
    }

    companion object {
        fun of(
            customId: String = "",
            title: String = "",
            inputs: List<AutoModalInput> = listOf(),
            onSubmit: ((ModalInteractionEvent) -> Unit)? = null,
        ): AutoModal {
            return Builder()
                .customId(customId)
                .title(title)
                .inputs(inputs)
                .onSubmit(onSubmit)
                .build()
        }
    }
}
