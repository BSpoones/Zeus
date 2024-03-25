package org.bspoones.zeus.command.annotations.choices.variable

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class VariableLongChoices(
    val id: String,
)