//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.infrastructure.logging

import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.util.Supplier

/**
 * Logs an error message computed only if needed.
 */
fun Logger.logError(messageMaker: () -> String) {
    this.error(Supplier<String> {
        messageMaker()
    })
}

/**
 * Logs an info message computed only if needed.
 */
fun Logger.logInfo(messageMaker: () -> String) {
    this.info(Supplier<String> {
        messageMaker()
    })
}

/**
 * Logs a warning message computed only if needed.
 */
fun Logger.logWarn(messageMaker: () -> String) {
    this.warn(Supplier<String> {
        messageMaker()
    })
}

