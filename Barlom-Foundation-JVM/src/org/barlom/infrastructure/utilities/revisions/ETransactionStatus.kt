//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.utilities.revisions

/**
 * Status of a transaction.
 */
enum class ETransactionStatus {
    NO_TRANSACTION,
    IN_PROGRESS,
    COMMITTED,
    ABORTED;
}
