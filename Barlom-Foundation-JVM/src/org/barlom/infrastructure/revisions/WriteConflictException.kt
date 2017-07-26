//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.revisions

/**
 * Exception indicating a failed transaction due to a concurrent transaction writing values before the completion of the
 * current transaction that has read older versions of those values.
 */
class WriteConflictException
    : RuntimeException("Transaction failed due to a write conflict.")
