package org.bspoones.zeus.command.annotations.choices.variable

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class VariableDoubleChoices(
    val id: String,
)