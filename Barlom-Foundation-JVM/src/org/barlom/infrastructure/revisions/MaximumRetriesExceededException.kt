//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.revisions

/**
 * Exception thrown when a transaction fails within its specified number of retries..
 */
class MaximumRetriesExceededException
    : RuntimeException("Maximum retries exceeded.")
