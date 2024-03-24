package org.bspoones.chloris.command.handler

import org.bspoones.chloris.command.annotations.GuildOnly
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.hasAnnotation

object GuildOnlyHandler {
    fun buildGuildOnly(method: KFunction<*>) : Boolean{
        return method.hasAnnotation<GuildOnly>()
    }

    fun buildGuildOnly(clazz: KClass<*>) : Boolean{
        return clazz.hasAnnotation<GuildOnly>()
    }
}