//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.revisions

/**
 * Exception thrown when a nested transaction is aborted.
 */
class NestedStmTransactionAbortedException(
    e: Exception?
) : RuntimeException("Nested transaction aborted. (Partial recovery not supported.)", e)
