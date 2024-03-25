package org.bspoones.zeus.command.annotations.choices

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class StringChoices(
    val choices: Array<String>
)