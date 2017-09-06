//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices2


/**
 * Interface for an abstract packaged element.
 */
abstract class AbstractPackagedElement internal constructor() : AbstractNamedElement() {

    /** The package containing this packaged element. */
    abstract val parents: List<Package>

    /** The dot-delimited path to this packaged element. */
    abstract val path: String


    /** Whether the given [pkg] is a direct parent of this element. */
    abstract fun hasParent(pkg: Package): Boolean

}