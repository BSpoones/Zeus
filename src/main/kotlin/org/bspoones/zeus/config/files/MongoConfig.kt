package org.bspoones.zeus.config.files

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import org.bspoones.zeus.config.annotations.ConfigDirectory
import org.bspoones.zeus.config.base.ActionableConfig
import org.bspoones.zeus.logging.getZeusLogger
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import java.net.URLEncoder
import java.nio.file.WatchEvent
import org.bspoones.zeus.storage.MongoConnection

private fun String.URLEncode() = URLEncoder.encode(this, "UTF-8")

@ConfigSerializable
@ConfigDirectory("zeus")
internal class MongoConfig: ActionableConfig() {

    var mongoEnabled: Boolean = true

    var address: String = "localhost"
    var port: Int = 27017
    var databaseName: String = ""
    var username: String = ""
    var password: String = ""

    private fun connectionString(): String {
        val x =  "mongodb://" +
                username.URLEncode() +
                ":" +
                password.URLEncode() +
                "@" +
                address.URLEncode() +
                ":" +
                port +
                "/" +
                databaseName.URLEncode() +
                "?ssl=false"

        println("ADDRESS IS $x")

        // TODO -> SSL this lol

        return x
    }

    fun settings(): MongoClientSettings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(connectionString()))
        .build()

    override fun onChange(event: WatchEvent<*>) {
        val logger = getZeusLogger("MongoConfig")

        logger.info("MongoConfig has changed, restarting Mongo Connection!")
        MongoConnection.setup()
    }

}