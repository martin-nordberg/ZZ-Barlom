package org.katydom.infrastructure

/**
 * Ensures a precondition is met; throws an exception if not.
 */
inline fun require(condition: Boolean, failureMessage : ()->String ) {

    if ( !condition ) {
        throw IllegalStateException( failureMessage() )
    }

}

inline fun requireArg(condition: Boolean, failureMessage : ()->String ) {

    if ( !condition ) {
        throw IllegalArgumentException( failureMessage() )
    }

}

inline fun <T> requireNonNull(value : T?, failureMessage : ()->String ) : T {

    if ( value == null ) {
        throw IllegalArgumentException( failureMessage() )
    }

    return value

}
