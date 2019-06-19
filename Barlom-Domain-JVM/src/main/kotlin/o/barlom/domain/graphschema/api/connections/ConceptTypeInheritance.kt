//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.ConceptType
import o.barlom.infrastructure.graphs.ConnectionType
import o.barlom.infrastructure.graphs.Id
import o.barlom.infrastructure.propertygraphs.IDirectedPropertyConnection
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * A connection from derived (sub) concept type to base (super) concept type.
 */
data class ConceptTypeInheritance(
    override val uuid: Uuid,
    override val fromConceptId: Id<ConceptType>,
    override val toConceptId: Id<ConceptType>
) : IDirectedPropertyConnection<ConceptTypeInheritance, ConceptType, ConceptType> {

    val subTypeId
        get() = fromConceptId

    val superTypeId
        get() = toConceptId

    override val type = TYPE

    ////

    companion object {
        val TYPE = ConnectionType<ConceptTypeInheritance>("ConceptTypeInheritance")
    }

}

//---------------------------------------------------------------------------------------------------------------------

