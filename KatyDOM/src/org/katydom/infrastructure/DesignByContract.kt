//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.katydom.infrastructure

/**
 * Ensures a internal precondition is met; throws an IllegalStateException if not.
 * @param condition boolean condition expected to be true.
 * @param failureMessage function producing the error message to throw if the condition is false.
 */
inline fun require(condition: Boolean, failureMessage : ()->String ) {

    if ( !condition ) {
        throw IllegalStateException( failureMessage() )
    }

}

/**
 * Ensures an argument precondition is met; throws an IllegalArgumentException if not.
 * @param condition boolean condition expected to be true.
 * @param failureMessage function producing the error message to throw if the condition is false.
 */
inline fun requireArg(condition: Boolean, failureMessage : ()->String ) {

    if ( !condition ) {
        throw IllegalArgumentException( failureMessage() )
    }

}

/**
 * Ensures an argument value typically something related to the argument itself is not null; throws
 * an IllegalArgumentException if it is null.
 * @param value value that must be non-null for .
 * @param failureMessage function producing the error message to throw if the condition is false.
 */
inline fun <T> requireNonNull(value : T?, failureMessage : ()->String ) : T {

    if ( value == null ) {
        throw IllegalArgumentException( failureMessage() )
    }

    return value

}
