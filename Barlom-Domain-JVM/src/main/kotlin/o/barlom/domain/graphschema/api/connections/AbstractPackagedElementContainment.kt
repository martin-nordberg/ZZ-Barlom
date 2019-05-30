//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.AbstractPackagedConcept
import o.barlom.domain.graphschema.api.concepts.Package
import o.barlom.infrastructure.propertygraphs.IDirectedPropertyConnection

//---------------------------------------------------------------------------------------------------------------------

/**
 * Interface to a connection representing containment within a package.
 */
abstract class AbstractPackagedElementContainment<Connection, ChildConcept : AbstractPackagedConcept<ChildConcept>>
    : IDirectedPropertyConnection<Connection, Package, ChildConcept> {

    /** Whether the contained concept is shared outside the package. */
    abstract var isShared: Boolean

    override fun get(propertyName: String): Any? =
        when (propertyName) {
            "isShared" -> isShared
            else       -> super.get(propertyName)
        }

    override fun hasProperty(propertyName: String): Boolean =
        when (propertyName) {
            "isShared" -> true
            else       -> super.hasProperty(propertyName)
        }

    override fun propertyNames(): Set<String> =
        super.propertyNames().plus("isShared")

    override fun setBoolean(propertyName: String, value: Boolean) =
        when (propertyName) {
            "isShared" -> isShared = value
            else       -> super.setBoolean(propertyName, value)
        }

}

//---------------------------------------------------------------------------------------------------------------------

