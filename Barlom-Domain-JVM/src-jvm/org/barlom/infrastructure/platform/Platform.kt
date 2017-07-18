//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.platform

import java.time.Instant


/**
 * Converts a long to a hex string.
 */
fun longToHexString( value: Long ) : String {
    return java.lang.Long.toHexString( value )
}

/**
 * Platform-specific type for date/time values.
 */
typealias DateTime = Instant