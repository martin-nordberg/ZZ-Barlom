//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.ConceptPropertyType
import o.barlom.domain.graphschema.api.concepts.ConstrainedDataType
import o.barlom.infrastructure.graphs.Id
import o.barlom.infrastructure.propertygraphs.IDirectedPropertyConnection

//---------------------------------------------------------------------------------------------------------------------

/**
 * A connection from a concept property type to its data type.
 */
data class ConceptPropertyDataTypeUsage(
    override val id: Id<ConceptPropertyDataTypeUsage>,
    override val fromConceptId: Id<ConceptPropertyType>,
    override val toConceptId: Id<ConstrainedDataType>
) : IDirectedPropertyConnection<ConceptPropertyDataTypeUsage, ConceptPropertyType, ConstrainedDataType> {

    val conceptPropertyTypeId
        get() = fromConceptId

    val dataTypeId
        get() = toConceptId

}

//---------------------------------------------------------------------------------------------------------------------

