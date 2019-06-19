//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.AbstractPackagedConcept
import o.barlom.domain.graphschema.api.concepts.Package
import o.barlom.domain.graphschema.api.types.ESharing
import o.barlom.infrastructure.propertygraphs.IDirectedPropertyConnection

//---------------------------------------------------------------------------------------------------------------------

/**
 * Interface to a connection representing containment within a package.
 */
abstract class AbstractPackagedElementContainment<
    Connection : AbstractPackagedElementContainment<Connection, ChildConcept>,
    ChildConcept : AbstractPackagedConcept<ChildConcept>
> : IDirectedPropertyConnection<Connection, Package, ChildConcept> {

    /** Whether the contained concept is shared outside the package. */
    abstract val sharing: ESharing

    val parentPackageId
        get() = fromConceptId

    val childElementId
        get() = toConceptId

    ////

    override fun get(propertyName: String): Any? =
        when (propertyName) {
            "sharing" -> sharing
            else      -> super.get(propertyName)
        }

    override fun hasProperty(propertyName: String): Boolean =
        when (propertyName) {
            "sharing" -> true
            else      -> super.hasProperty(propertyName)
        }

    override fun propertyNames(): Set<String> =
        super.propertyNames().plus("sharing")

}

//---------------------------------------------------------------------------------------------------------------------

