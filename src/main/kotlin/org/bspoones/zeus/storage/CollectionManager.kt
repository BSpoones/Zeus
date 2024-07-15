package org.bspoones.zeus.storage

import com.mongodb.client.model.Filters
import org.bspoones.zeus.config.files.MongoConfig
import org.bspoones.zeus.logging.getZeusLogger
import kotlin.math.log
import kotlin.reflect.KClass

fun registerEntity(vararg entities: KClass<out MongoEntity>) {
    entities.forEach {
        CollectionManager.registerEntity(it)
    }
}

inline fun <reified T : MongoEntity> getEntity(id: Any): T? {
    return CollectionManager.getEntity(T::class, id)
}

object CollectionManager {
    private val logger = getZeusLogger("Mongo")

    private val registeredCollections: MutableSet<KClass<out MongoEntity>> = mutableSetOf()

    fun registerEntity(clazz: KClass<out MongoEntity>) {
        MongoConnection.validityCheck()

        logger.debug("Registering ${clazz.simpleName}")
        try {
            registeredCollections.add(clazz)
            MongoConnection.store().mapper.map(clazz.java)
        } catch (e: Exception) {
            logger.error("Failed to map ${clazz.simpleName} - $e")
        }
    }

    fun <T : MongoEntity> getEntity(clazz: KClass<out T>, id: Any): T? {
        return MongoConnection.store()
            .getCollection(clazz.java)
            .find(Filters.eq("_id", id))
            .firstOrNull()
    }

    fun <T : MongoEntity> save(entity: T) {
        MongoConnection
            .store()
            .save(entity.javaClass)
    }

    fun <T : MongoEntity> delete(entity: T): Boolean {
        return MongoConnection.store().delete(entity).wasAcknowledged()
    }

}