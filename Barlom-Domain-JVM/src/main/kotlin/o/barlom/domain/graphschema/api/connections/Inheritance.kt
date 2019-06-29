//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.AbstractNamedConcept
import o.barlom.domain.graphschema.api.concepts.ConceptType
import o.barlom.domain.graphschema.api.concepts.DirectedConnectionType
import o.barlom.domain.graphschema.api.concepts.UndirectedConnectionType
import o.barlom.infrastructure.graphs.ConnectionTypeId
import o.barlom.infrastructure.graphs.Id
import o.barlom.infrastructure.propertygraphs.IDirectedPropertyConnection
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * An inheritance connection from sub concept to super concept.
 */
data class Inheritance<
    Concept : AbstractNamedConcept<Concept>
    >(
    override val typeId: ConnectionTypeId<Inheritance<Concept>>,
    override val uuid: Uuid,
    override val fromConceptId: Id<Concept>,
    override val toConceptId: Id<Concept>
) : IDirectedPropertyConnection<Inheritance<Concept>, Concept, Concept> {

    val subTypeId
        get() = fromConceptId

    val superTypeId
        get() = toConceptId

    ////

    init {
        require(Inheritance.TYPE_NAMES.contains(typeId.typeName))
    }

    ////

    companion object {

        val CONCEPT_TYPE_INHERITANCE_TYPE = ConnectionTypeId<Inheritance<ConceptType>>(
            "o.barlom.domain.graphschema.api.connections.ConceptTypeInheritance"
        )

        val DIRECTED_CONNECTION_TYPE_INHERITANCE_TYPE = ConnectionTypeId<DirectedConnectionTypeInheritance>(
            "o.barlom.domain.graphschema.api.connections.DirectedConnectionTypeInheritance"
        )

        val UNDIRECTED_CONNECTION_TYPE_INHERITANCE_TYPE = ConnectionTypeId<UndirectedConnectionTypeInheritance>(
            "o.barlom.domain.graphschema.api.connections.UndirectedConnectionTypeInheritance"
        )

        private val TYPE_NAMES = setOf(
            CONCEPT_TYPE_INHERITANCE_TYPE.typeName,
            DIRECTED_CONNECTION_TYPE_INHERITANCE_TYPE.typeName,
            UNDIRECTED_CONNECTION_TYPE_INHERITANCE_TYPE.typeName
        )

    }

}

//---------------------------------------------------------------------------------------------------------------------

typealias ConceptTypeInheritance = Inheritance<ConceptType>

//---------------------------------------------------------------------------------------------------------------------

typealias DirectedConnectionTypeInheritance = Inheritance<DirectedConnectionType>

//---------------------------------------------------------------------------------------------------------------------

typealias UndirectedConnectionTypeInheritance = Inheritance<UndirectedConnectionType>

//---------------------------------------------------------------------------------------------------------------------

