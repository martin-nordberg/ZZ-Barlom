//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.barlom.infrastructure.revisions

/**
 * Exception thrown when a transaction fails within its specified number of retries..
 */
class MaximumRetriesExceededException
    : RuntimeException("Maximum retries exceeded.")
