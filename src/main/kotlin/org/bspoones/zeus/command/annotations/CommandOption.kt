package org.bspoones.zeus.command.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class CommandOption(
    val name: String,
    val description: String = " ",
    val isRequired: Boolean = true,
    val autoComplete: Boolean = false
)
