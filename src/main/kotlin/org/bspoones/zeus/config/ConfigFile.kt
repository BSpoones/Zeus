package org.bspoones.zeus.config

import org.bspoones.zeus.NAME
import org.bspoones.zeus.ZeusInstance
import org.bspoones.zeus.logging.getZeusLogger
import org.slf4j.LoggerFactory
import org.spongepowered.configurate.BasicConfigurationNode
import org.spongepowered.configurate.gson.GsonConfigurationLoader
import org.spongepowered.configurate.reference.ConfigurationReference
import org.spongepowered.configurate.reference.ValueReference
import org.spongepowered.configurate.reference.WatchServiceListener
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.math.log

private const val PATH_NAME = "config"


class ConfigFile<T>(val clazz: Class<T>, val directory: String = "") : AutoCloseable {
    private val logger = getZeusLogger("$NAME | ConfigFile ")


    // Allows for nested config files
    private val realDirectory = if (directory == "") "" else "$directory${File.separator}"
    private val directoryPath = Paths.get(PATH_NAME + File.separator + realDirectory)
    private val filePath: Path = directoryPath.resolve("${clazz.simpleName}.json")

    private lateinit var ref: ConfigurationReference<BasicConfigurationNode>
    private lateinit var configFile: ValueReference<T, BasicConfigurationNode>

    private val watchListener: WatchServiceListener = WatchServiceListener.create()
    private var setup: Boolean = false

    init {
        try {
            createFile()
            setupListener()

        } catch (t: Throwable) {
            logger.error("Failed to create config file for ${clazz.simpleName} - $t")
        }
    }

    private fun createFile() {
        if (Files.notExists(directoryPath)) {
            Files.createDirectories(directoryPath)
        }
        if (Files.notExists(filePath)) {
            Files.createFile(filePath)
        }
    }

    private fun createLoader(path: Path): GsonConfigurationLoader {
        return GsonConfigurationLoader.builder()
            .defaultOptions {
                it.shouldCopyDefaults(true)
            }
            .path(path)
            .build()
    }

    private fun setupListener() {
        ref = watchListener.listenToConfiguration(
            { createLoader(it) },
            filePath
        )
        configFile = ref.referenceTo(clazz)

        watchListener.listenToFile(filePath) { event ->
            // TODO -> Figure out why a command should be changed wtf?
//            if (!setup || ZeusInstance.instance ==  null) return@listenToFile // Stops the init from updating commands
//            logger.info("Config changed, re-registering commands")
//            ZeusInstance.instance?.registerCommands(true)
        }

        ref.save()
        setup = true

    }

    fun getConfig(): T {
        return this.configFile.get()!!
    }

    override fun close() {
        this.watchListener.close()
        this.ref.close()
    }
}