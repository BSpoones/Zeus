package org.bspoones.zeus.logging

import org.bspoones.zeus.NAME
import org.slf4j.LoggerFactory

var SHOULD_LOG: Boolean = true

fun getZeusLogger(name: String) = ZeusLogger(name)

class ZeusLogger(name: String) {
    private val logger = LoggerFactory.getLogger("$NAME | $name")

    fun debug(msg: String) {
        if (SHOULD_LOG) logger.debug(msg)
    }

    fun info(msg: String) {
        if (SHOULD_LOG) logger.info(msg)
    }
    fun warn(msg: String) {
        if (SHOULD_LOG) logger.warn(msg)
    }
    fun error(msg: String) {
        if (SHOULD_LOG) logger.error(msg)
    }
    fun trace(msg: String) {
        if (SHOULD_LOG) logger.trace(msg)
    }
}