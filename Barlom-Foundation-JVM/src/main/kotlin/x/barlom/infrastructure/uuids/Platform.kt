//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package /*jvm*/x.barlom.infrastructure.uuids


/**
 * Converts a long to a hex string.
 */
fun longToHexString( value: Long ) : String {
    return java.lang.Long.toHexString( value )
}
