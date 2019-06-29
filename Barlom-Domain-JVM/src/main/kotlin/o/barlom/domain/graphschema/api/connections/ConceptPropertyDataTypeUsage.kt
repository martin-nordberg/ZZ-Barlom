//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.ConceptPropertyType
import o.barlom.domain.graphschema.api.concepts.ConstrainedBoolean
import o.barlom.infrastructure.graphs.ConnectionTypeId
import o.barlom.infrastructure.graphs.Id
import o.barlom.infrastructure.propertygraphs.IDirectedPropertyConnection
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * A connection from a concept property type to its boolean data type.
 */
data class ConceptPropertyBooleanUsage(
    override val uuid: Uuid,
    override val fromConceptId: Id<ConceptPropertyType>,
    override val toConceptId: Id<ConstrainedBoolean>
) : IDirectedPropertyConnection<ConceptPropertyBooleanUsage, ConceptPropertyType, ConstrainedBoolean> {

    val conceptPropertyTypeId
        get() = fromConceptId

    val dataTypeId
        get() = toConceptId

    override val typeId = TYPE

    ////

    companion object {
        val TYPE = ConnectionTypeId<ConceptPropertyBooleanUsage>(
            "o.barlom.domain.graphschema.api.connections.ConceptPropertyBooleanUsage"
        )
    }

}

//---------------------------------------------------------------------------------------------------------------------

