package org.bspoones.zeus.logging

import org.bspoones.zeus.NAME
import org.slf4j.LoggerFactory

var SHOULD_LOG: Boolean = true

internal fun getZeusLogger(name: String) = ZeusLogger(name)

internal class ZeusLogger(name: String) {
    private val logName = if (name == "") NAME else "$NAME |"

    private val logger = LoggerFactory.getLogger(logName)

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