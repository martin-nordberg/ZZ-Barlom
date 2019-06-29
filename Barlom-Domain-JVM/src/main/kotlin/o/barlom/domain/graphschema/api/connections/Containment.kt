//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.*
import o.barlom.domain.graphschema.api.types.ESharing
import o.barlom.infrastructure.graphs.ConnectionTypeId
import o.barlom.infrastructure.graphs.Id
import o.barlom.infrastructure.propertygraphs.IDirectedPropertyConnection
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * A connection from parent package to contained child package.
 */
data class Containment<
    ParentConcept : AbstractNamedConcept<ParentConcept>,
    ChildConcept : AbstractNamedConcept<ChildConcept>
    >(
    override val typeId: ConnectionTypeId<Containment<ParentConcept, ChildConcept>>,
    override val uuid: Uuid,
    override val fromConceptId: Id<ParentConcept>,
    override val toConceptId: Id<ChildConcept>,
    var sharing: ESharing = ESharing.SHARED
) : IDirectedPropertyConnection<Containment<ParentConcept, ChildConcept>, ParentConcept, ChildConcept> {

    val parentId
        get() = fromConceptId

    val childId
        get() = toConceptId

    ////

    init {
        require(TYPE_NAMES.contains(typeId.typeName))
    }

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

    ////

    companion object {

        val CONCEPT_PROPERTY_TYPE_CONTAINMENT_TYPE = ConnectionTypeId<ConceptPropertyTypeContainment>(
            "o.barlom.domain.graphschema.api.connections.ConceptPropertyTypeContainment"
        )

        val CONCEPT_TYPE_CONTAINMENT_TYPE = ConnectionTypeId<Containment<Module, ConceptType>>(
            "o.barlom.domain.graphschema.api.connections.ConceptTypeContainment"
        )

        val CONSTRAINED_BOOLEAN_CONTAINMENT_TYPE = ConnectionTypeId<ConstrainedBooleanContainment>(
            "o.barlom.domain.graphschema.api.connections.ConstrainedBooleanContainment"
        )

        val DIRECTED_CONNECTION_PROPERTY_TYPE_CONTAINMENT_TYPE =
            ConnectionTypeId<DirectedConnectionPropertyTypeContainment>(
                "o.barlom.domain.graphschema.api.connections.DirectedConnectionPropertyTypeContainment"
            )

        val DIRECTED_CONNECTION_TYPE_CONTAINMENT_TYPE = ConnectionTypeId<DirectedConnectionTypeContainment>(
            "o.barlom.domain.graphschema.api.connections.DirectedConnectionTypeContainment"
        )

        val MODULE_CONTAINMENT_TYPE = ConnectionTypeId<Containment<Module, Module>>(
            "o.barlom.domain.graphschema.api.connections.ModuleContainment"
        )

        val UNDIRECTED_CONNECTION_PROPERTY_TYPE_CONTAINMENT_TYPE =
            ConnectionTypeId<UndirectedConnectionPropertyTypeContainment>(
                "o.barlom.domain.graphschema.api.connections.UndirectedConnectionPropertyTypeContainment"
            )

        val UNDIRECTED_CONNECTION_TYPE_CONTAINMENT_TYPE = ConnectionTypeId<UndirectedConnectionTypeContainment>(
            "o.barlom.domain.graphschema.api.connections.UndirectedConnectionTypeContainment"
        )

        private val TYPE_NAMES = setOf(
            CONCEPT_PROPERTY_TYPE_CONTAINMENT_TYPE.typeName,
            CONCEPT_TYPE_CONTAINMENT_TYPE.typeName,
            CONSTRAINED_BOOLEAN_CONTAINMENT_TYPE.typeName,
            DIRECTED_CONNECTION_PROPERTY_TYPE_CONTAINMENT_TYPE.typeName,
            DIRECTED_CONNECTION_TYPE_CONTAINMENT_TYPE.typeName,
            MODULE_CONTAINMENT_TYPE.typeName,
            UNDIRECTED_CONNECTION_PROPERTY_TYPE_CONTAINMENT_TYPE.typeName,
            UNDIRECTED_CONNECTION_TYPE_CONTAINMENT_TYPE.typeName
        )

    }

}

//---------------------------------------------------------------------------------------------------------------------

typealias ConceptPropertyTypeContainment = Containment<ConceptType, ConceptPropertyType>

//---------------------------------------------------------------------------------------------------------------------

typealias ConceptTypeContainment = Containment<Module, ConceptType>

//---------------------------------------------------------------------------------------------------------------------

typealias ConstrainedBooleanContainment = Containment<Module, ConstrainedBoolean>

//---------------------------------------------------------------------------------------------------------------------

typealias DirectedConnectionPropertyTypeContainment = Containment<DirectedConnectionType, ConnectionPropertyType>

//---------------------------------------------------------------------------------------------------------------------

typealias DirectedConnectionTypeContainment = Containment<Module, DirectedConnectionType>

//---------------------------------------------------------------------------------------------------------------------

typealias ModuleContainment = Containment<Module, Module>

//---------------------------------------------------------------------------------------------------------------------

typealias UndirectedConnectionPropertyTypeContainment = Containment<UndirectedConnectionType, ConnectionPropertyType>

//---------------------------------------------------------------------------------------------------------------------

typealias UndirectedConnectionTypeContainment = Containment<Module, UndirectedConnectionType>

//---------------------------------------------------------------------------------------------------------------------

