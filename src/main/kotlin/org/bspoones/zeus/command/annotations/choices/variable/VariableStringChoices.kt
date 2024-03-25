package org.bspoones.zeus.command.annotations.choices.variable

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class VariableStringChoices(
    val id: String,
)