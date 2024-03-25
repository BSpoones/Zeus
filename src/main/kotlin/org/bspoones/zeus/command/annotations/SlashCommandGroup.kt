package org.bspoones.zeus.command.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class SlashCommandGroup(val name: String, val description: String = " ")