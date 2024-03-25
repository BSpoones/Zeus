package org.bspoones.zeus.command.annotations.command

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class MessageCommand(val name: String, val description: String = " ")