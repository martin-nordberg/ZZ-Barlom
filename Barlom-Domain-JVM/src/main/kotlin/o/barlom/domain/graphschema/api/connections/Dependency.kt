//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.connections

import o.barlom.domain.graphschema.api.concepts.AbstractDocumentedConcept
import o.barlom.domain.graphschema.api.concepts.Module
import o.barlom.infrastructure.graphs.ConnectionTypeId
import o.barlom.infrastructure.graphs.Id
import o.barlom.infrastructure.propertygraphs.IDirectedPropertyConnection
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * A connection from consumer module to supplier module.
 */
data class Dependency<
    Concept : AbstractDocumentedConcept<Concept>
    >(
    override val typeId: ConnectionTypeId<Dependency<Concept>>,
    override val uuid: Uuid,
    override val fromConceptId: Id<Concept>,
    override val toConceptId: Id<Concept>
    // TODO: exporting
) : IDirectedPropertyConnection<Dependency<Concept>, Concept, Concept> {

    val consumerId
        get() = fromConceptId

    val supplierId
        get() = toConceptId

    ////

    companion object {
        val MODULE_DEPENDENCY_TYPE = ConnectionTypeId<ModuleDependency>(
            "o.barlom.domain.graphschema.api.connections.ModuleDependency"
        )
    }

}

//---------------------------------------------------------------------------------------------------------------------

typealias ModuleDependency = Dependency<Module>

//---------------------------------------------------------------------------------------------------------------------

