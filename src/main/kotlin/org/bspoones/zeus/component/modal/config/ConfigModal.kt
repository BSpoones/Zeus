package org.bspoones.zeus.component.modal.config

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent
import net.dv8tion.jda.internal.interactions.modal.ModalImpl
import org.bspoones.zeus.component.modal.AutoModal
import org.bspoones.zeus.util.text.parse
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class ConfigModal(
    var customId: String = "",
    var title: String = "",
    var inputs: MutableList<ConfigModalInput> = mutableListOf()
) {

    fun toConfigModal(vararg placeholders: Any, onSubmit: (ModalInteractionEvent) -> Unit = {}): AutoModal {
        return AutoModal.Builder()
            .setCustomId(customId.parse(*placeholders))
            .setTitle(title.parse(*placeholders))
            .setInputs(inputs.map { it.toAutoModalInput(*placeholders) })
            .setOnSubmit(onSubmit)
            .build()
    }

    fun toModal(vararg placeholders: Any, onSubmit: (ModalInteractionEvent) -> Unit = {}): ModalImpl {
        return toConfigModal(*placeholders, onSubmit = onSubmit).toModal()
    }

}