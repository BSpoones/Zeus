package org.bspoones.zeus.command.handler

import net.dv8tion.jda.api.entities.channel.Channel
import net.dv8tion.jda.api.interactions.commands.Command
import org.bspoones.zeus.command.CommandRegistry
import org.bspoones.zeus.command.annotations.choices.*
import org.bspoones.zeus.command.annotations.choices.variable.*
import kotlin.reflect.KParameter
import kotlin.reflect.full.hasAnnotation

object ChoiceHandler {
    private val CHOICES = mapOf(
        StringChoices::class to String::class,
        LongChoices::class to Int::class,
        DoubleChoices::class to Float::class,
        ChannelTypes::class to Channel::class
    )

    private val VARIABLE_CHOICES = mapOf(
        VariableStringChoices::class to String::class,
        VariableLongChoices::class to Int::class,
        VariableDoubleChoices::class to Float::class,
    )

    fun buildChoices(parameter: KParameter): List<Command.Choice> {
        return getChoices(parameter).mapNotNull { choice ->
            when (choice) {
                is String -> Command.Choice(choice, choice)
                is Double -> Command.Choice(choice.toString(), choice)
                is Long -> Command.Choice(choice.toString(), choice)
                else -> null
            }
        }
    }

    fun getChoices(parameter: KParameter): List<Any> {
        val choiceAnnotations = parameter.annotations.filter { annotation ->
            (CHOICES + VARIABLE_CHOICES).keys.map { it.simpleName }.contains(annotation.annotationClass.simpleName)
        }

        if (choiceAnnotations.isEmpty()) return listOf()
        if (choiceAnnotations.size > 1) throw IllegalArgumentException("You can only have one choice type")

        if (parameter.type::class != (CHOICES + VARIABLE_CHOICES)[choiceAnnotations.first().annotationClass])
            throw IllegalArgumentException("A parameter choice must be of the same type as the parameter")

        return when (val annotation = choiceAnnotations.first()) {
            is StringChoices -> annotation.choices.toList()
            is DoubleChoices -> annotation.choices.toList()
            is LongChoices -> annotation.choices.toList()
            is VariableStringChoices -> getVariableChoices(annotation)
            is VariableDoubleChoices ->getVariableChoices(annotation)
            is VariableLongChoices ->getVariableChoices(annotation)
            else -> throw IllegalArgumentException("Invalid Choice annotation")
        }
    }

    fun getVariableChoices(annotation: Annotation): List<Any> {
        val id = when(annotation) {
            is VariableStringChoices -> annotation.id
            is VariableDoubleChoices -> annotation.id
            is VariableLongChoices -> annotation.id
            else -> throw IllegalArgumentException("Invalid annotation type")
        }

        val result: Any = CommandRegistry.getVariableChoice(id).invoke()
        return when {
            result is List<*> -> result.filterIsInstance<Any>()
            else -> throw IllegalArgumentException("Variable method must return a List")
        }
    }

}