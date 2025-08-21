package org.bspoones.zeus.storage

import dev.morphia.annotations.Entity
import org.bspoones.zeus.util.scheduling.async

@Entity
abstract class MongoEntity {
    abstract fun id(): Any

    fun save() {
        async {
            CollectionManager.save(this)
        }
    }

    fun delete() = CollectionManager.delete(this)
}