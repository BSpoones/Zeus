package org.bspoones.zeus.command.handler

import org.bspoones.zeus.command.annotations.GuildOnly
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.hasAnnotation

/**
 * Determining if an object or method is guildOnly
 *
 * @see org.bspoones.zeus.command.annotations.GuildOnly
 * @author <a href="https://www.bspoones.com">BSpoones</a>
 */
object GuildOnlyHandler {
    fun buildGuildOnly(method: KFunction<*>) : Boolean{
        return method.hasAnnotation<GuildOnly>()
    }

    fun buildGuildOnly(clazz: KClass<*>) : Boolean{
        return clazz.hasAnnotation<GuildOnly>()
    }
}