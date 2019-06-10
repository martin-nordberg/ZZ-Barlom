//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.concepts

import o.barlom.domain.graphschema.api.types.EAbstractness
import o.barlom.domain.graphschema.api.types.ECyclicity
import o.barlom.domain.graphschema.api.types.EMultiEdgedness
import o.barlom.domain.graphschema.api.types.ESelfLooping

//---------------------------------------------------------------------------------------------------------------------

/**
 * The type of a connection (edge or link).
 */
abstract class AbstractConnectionType<ConnectionType : AbstractConnectionType<ConnectionType>>
    : AbstractPackagedConcept<ConnectionType>() {

    /** Whether this connection type is abstract. */
    abstract val abstractness: EAbstractness

    /** Whether this connection type is acyclic. */
    abstract val cyclicity: ECyclicity

    /** Whether this is a root connection type (directed or undirected). */
    abstract val isRoot: Boolean

    /** Whether this connection type allows multiple connections between two given concepts. */
    abstract val multiEdgedness: EMultiEdgedness

    /** Whether this connection type allows a connection from a concept to itself. */
    abstract val selfLooping: ESelfLooping

    override fun get(propertyName: String): Any? =
        when (propertyName) {
            "abstractness"   -> abstractness
            "cyclicity"      -> cyclicity
            "isRoot"         -> isRoot
            "multiEdgedness" -> multiEdgedness
            "selfLooping"    -> selfLooping
            else             -> super.get(propertyName)
        }

    override fun hasProperty(propertyName: String): Boolean =
        when (propertyName) {
            "abstractness"   -> true
            "cyclicity"      -> true
            "isRoot"         -> true
            "multiEdgedness" -> true
            "selfLooping"    -> true
            else             -> super.hasProperty(propertyName)
        }

    override fun propertyNames(): Set<String> =
        super.propertyNames()
            .plus("abstractness")
            .plus("cyclicity")
            .plus("isRoot")
            .plus("multiEdgedness")
            .plus("selfLooping")

}

//---------------------------------------------------------------------------------------------------------------------

