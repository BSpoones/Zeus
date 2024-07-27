package org.bspoones.zeus.config

import org.bspoones.zeus.command.CommandRegistry
import org.bspoones.zeus.command.config.CommandConfig
import org.bspoones.zeus.config.annotations.ConfigDirectory
import kotlin.reflect.KClass
import org.bspoones.zeus.logging.getZeusLogger

inline fun <reified T : Any> getConfig(): T = ConfigManager.getConfig(T::class.java)
fun <T> getConfig(config: Class<T>): T = ConfigManager.getConfig(config)
fun <T : Any> initConfig(vararg clazzes: KClass<out T>) = clazzes.forEach { ConfigManager.initConfig(it.java) }

// TODO -> JavaDoc
object ConfigManager : AutoCloseable {
    private val logger = getZeusLogger("Config")
    private val configMap: MutableMap<Class<*>, ConfigFile<*>> = mutableMapOf()

    override fun close() {
        configMap.values.forEach {
            logger.debug("Closing ${it.clazz.simpleName}.json")
            it.close()
        }
    }

    fun <T> initConfig(config: Class<T>) {
        logger.debug("Initialising ${config.simpleName}")
        val directory = if (config.isAnnotationPresent(ConfigDirectory::class.java)) {
            config.getAnnotation(ConfigDirectory::class.java).directory
        } else ""

        try {
            configMap[config] = ConfigFile(config, directory)
        } catch (t: Throwable) {
            t.printStackTrace()
        }
        registerCommandConfig(config)

    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getConfig(config: Class<T>): T {
        logger.debug("Fetching config file of ${config.simpleName}")
        return configMap[config]?.getConfig() as T
            ?: throw ClassNotFoundException("The class ${config.simpleName} has not been initialised!")
    }

    private fun <T> registerCommandConfig(config: Class<T>) {
        val configFile = getConfig(config) ?: return
        if (configFile is CommandConfig) {
            CommandRegistry.registerCommandConfig(configFile)
        }
    }

}