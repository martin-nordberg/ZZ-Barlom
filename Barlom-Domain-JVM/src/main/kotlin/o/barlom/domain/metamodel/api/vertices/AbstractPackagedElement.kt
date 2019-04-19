//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.metamodel.api.vertices


/**
 * Interface for an abstract packaged element.
 */
abstract class AbstractPackagedElement internal constructor() : AbstractNamedElement() {

    /** The package containing this packaged element. */
    abstract val parents: List<Package>


    /** Whether the given [pkg] is a direct parent of this element. */
    abstract fun hasParent(pkg: Package): Boolean

}
