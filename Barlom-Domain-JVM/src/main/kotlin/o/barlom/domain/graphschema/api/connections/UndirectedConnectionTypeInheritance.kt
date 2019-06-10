//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.UndirectedConnectionType
import o.barlom.infrastructure.graphs.Id
import o.barlom.infrastructure.propertygraphs.IDirectedPropertyConnection

//---------------------------------------------------------------------------------------------------------------------

/**
 * An inheritance connection from sub undirected connection type to super undirected connection type.
 */
data class UndirectedConnectionTypeInheritance(
    override val id: Id<UndirectedConnectionTypeInheritance>,
    override val fromConceptId: Id<UndirectedConnectionType>,
    override val toConceptId: Id<UndirectedConnectionType>
) : IDirectedPropertyConnection<UndirectedConnectionTypeInheritance, UndirectedConnectionType, UndirectedConnectionType> {

    val subDirectedConnectionTypeId
        get() = fromConceptId

    val superDirectedConnectionTypeId
        get() = toConceptId

}

//---------------------------------------------------------------------------------------------------------------------

