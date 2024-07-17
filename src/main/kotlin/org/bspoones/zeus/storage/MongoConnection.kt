package org.bspoones.zeus.storage

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import dev.morphia.Datastore
import dev.morphia.Morphia
import org.bspoones.zeus.config.files.MongoConfig
import org.bspoones.zeus.config.getConfig
import org.bspoones.zeus.logging.getZeusLogger
import javax.naming.ConfigurationException
import kotlin.system.exitProcess

object MongoConnection {
    private val logger = getZeusLogger("Mongo Connection")
    private var client: MongoClient? = null
    private var store: Datastore? = null


    fun setup() {
        val config = getConfig<MongoConfig>()
        if (!config.mongoEnabled) {
            // Closing connection if config has been altered
            if (client != null) {
                client!!.close()
            }
            // Forcing null if config is newly disabled
            client = null
            store = null
            return
        }

        client = MongoClients.create(config.settings())
        store = Morphia.createDatastore(client!!, config.databaseName)
    }

    fun validityCheck() {
        if (store == null) {
            logger.error("Failed to load database, TODO")
            exitProcess(0)
        }
    }

    fun client(): MongoClient {
        return client ?: throw ConfigurationException("Invalid mongo configuration!")
    }

    fun store(): Datastore {
        return store ?: throw ConfigurationException("Invalid mongo configuration!")
    }
}