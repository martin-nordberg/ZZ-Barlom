//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.DirectedConnectionType
import o.barlom.infrastructure.graphs.Id
import o.barlom.infrastructure.propertygraphs.IDirectedPropertyConnection

//---------------------------------------------------------------------------------------------------------------------

/**
 * An inheritance connection from sub directed connection type to super directed connection type.
 */
data class DirectedConnectionTypeInheritance(
    override val id: Id<DirectedConnectionTypeInheritance>,
    override val fromConceptId: Id<DirectedConnectionType>,
    override val toConceptId: Id<DirectedConnectionType>
) : IDirectedPropertyConnection<DirectedConnectionTypeInheritance, DirectedConnectionType, DirectedConnectionType> {

    val subDirectedConnectionTypeId
        get() = fromConceptId

    val superDirectedConnectionTypeId
        get() = toConceptId

}

//---------------------------------------------------------------------------------------------------------------------

