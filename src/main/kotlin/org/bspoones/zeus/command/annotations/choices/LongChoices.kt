package org.bspoones.zeus.command.annotations.choices

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class LongChoices(
    val choices: LongArray = [],
)