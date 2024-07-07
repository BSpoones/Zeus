package org.bspoones.zeus.config

import org.slf4j.LoggerFactory
import kotlin.reflect.KClass
import org.bspoones.zeus.*
import org.bspoones.zeus.logging.getZeusLogger

private val logger = getZeusLogger("$NAME | Config")

fun <T : Any> getConfig(clazz: KClass<T>): T = ConfigManager.getConfig(clazz.java)
inline fun <reified T : Any> getConfig(): T = getConfig(T::class)

fun <T : Any> initConfig(vararg clazzes: KClass<T>) = clazzes.forEach { ConfigManager.initConfig(it.java) }
fun <T : Any> initConfig(clazz: KClass<T>, directory: String = "") = ConfigManager.initConfig(clazz.java, directory)
fun <T : Any> initConfig(map: Map<String, List<KClass<T>>>) = map.entries.forEach { (directory, classes) ->
    classes.forEach { clazz ->
        ConfigManager.initConfig(clazz.java, directory)
    }
}


object ConfigManager : AutoCloseable {
    private val configMap: MutableMap<Class<*>, ConfigFile<*>> = mutableMapOf()

    override fun close() {
        configMap.values.forEach {
            it.close()
        }
    }

    fun <T> initConfig(config: Class<T>, directory: String = "") {
        try {
            logger.debug("Initialising ${config.simpleName}")
            configMap[config] = ConfigFile(config, directory)
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getConfig(config: Class<T>): T {
        return configMap[config]!!.getConfig() as T
    }


}