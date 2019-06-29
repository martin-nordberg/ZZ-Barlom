//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.model

import o.barlom.domain.graphschema.api.concepts.ConceptType
import o.barlom.domain.graphschema.api.concepts.DirectedConnectionType
import o.barlom.domain.graphschema.api.concepts.Module
import o.barlom.domain.graphschema.api.concepts.UndirectedConnectionType
import o.barlom.domain.graphschema.api.queries.*
import o.barlom.infrastructure.graphs.IGraph
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

interface IModel
    : IModuleContainmentQueries,
    IModuleDependencyQueries,
    IConceptTypeContainmentQueries,
    IConceptTypeInheritanceQueries,
    IConceptPropertyTypeContainmentQueries {

    /** The graph at the core of this model. */
    override val graph: IGraph

    /** Function for creating new UUIDs. */
    val makeUuid: () -> Uuid

    /** The unique ID of this model. */
    val modelUuid: Uuid

    /** The root concept type. */
    val rootConceptType: ConceptType

    /** The root directed connection type. */
    val rootDirectedConnectionType: DirectedConnectionType

    /** The built-in root module containing everything else. */
    val rootModule: Module

    /** The built-in root undirected connection type. */
    val rootUndirectedConnectionType: UndirectedConnectionType

}

//---------------------------------------------------------------------------------------------------------------------

