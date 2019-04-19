//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.metamodel.api.vertices

/**
 * Interface to a named element - any model item with a name.
 */
abstract class AbstractNamedElement internal constructor() : AbstractDocumentedElement() {

    /** The name of this element. */
    abstract var name: String

    /** The dot-delimited path to this packaged element. */
    abstract val path: String

    /** Whether this element is contained directly or indirectly by the given package. */
    abstract fun hasParentPackage(pkg: Package): Boolean

}
