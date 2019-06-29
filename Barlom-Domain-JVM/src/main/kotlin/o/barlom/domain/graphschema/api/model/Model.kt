//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.model

import o.barlom.domain.graphschema.api.concepts.ConceptType
import o.barlom.domain.graphschema.api.concepts.DirectedConnectionType
import o.barlom.domain.graphschema.api.concepts.Module
import o.barlom.domain.graphschema.api.concepts.UndirectedConnectionType
import o.barlom.domain.graphschema.api.connections.ConceptTypeContainment
import o.barlom.domain.graphschema.api.connections.Containment
import o.barlom.domain.graphschema.api.connections.DirectedConnectionTypeContainment
import o.barlom.domain.graphschema.api.connections.UndirectedConnectionTypeContainment
import o.barlom.infrastructure.graphs.IWritableGraph
import o.barlom.infrastructure.graphs.graphOf
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

class Model(

    /** Function for creating new UUIDs */
    override val makeUuid: () -> Uuid,

    /** The unique ID for the root package within this model. */
    override val modelUuid: Uuid = Uuid.fromString("66522300-6c7d-11e7-81b7-080027b6d283")

) : IModel {

    var rootUuid = modelUuid

    /** The unique ID for the root package within this model. */
    private val rootPackageUuid = nextRootUuid()

    /** The unique ID for the root concept type within this model. */
    private val rootConceptTypeUuid = nextRootUuid()

    /** The unique ID for the root directed connection type within this model. */
    private val rootUndirectedConnectionTypeUuid = nextRootUuid()

    /** The unique ID for the root undirected connection type within this model. */
    private val rootDirectedConnectionTypeUuid = nextRootUuid()

    override val rootConceptType = ConceptType(rootConceptTypeUuid, true)

    override val rootDirectedConnectionType = DirectedConnectionType(rootDirectedConnectionTypeUuid, true)

    override val rootModule = Module(rootPackageUuid, true)

    override val rootUndirectedConnectionType = UndirectedConnectionType(rootUndirectedConnectionTypeUuid, true)

    ////

    override var graph = graphOf().update {
        addConcept(rootModule)
        addConcept(rootConceptType)
        addConcept(rootDirectedConnectionType)
        addConcept(rootUndirectedConnectionType)

        addConnection(ConceptTypeContainment(Containment.CONCEPT_TYPE_CONTAINMENT_TYPE, nextRootUuid(), rootModule, rootConceptType))
        addConnection(DirectedConnectionTypeContainment(Containment.DIRECTED_CONNECTION_TYPE_CONTAINMENT_TYPE, nextRootUuid(), rootModule, rootDirectedConnectionType))
        addConnection(UndirectedConnectionTypeContainment(Containment.UNDIRECTED_CONNECTION_TYPE_CONTAINMENT_TYPE, nextRootUuid(), rootModule, rootUndirectedConnectionType))
    }

    ////

    private fun nextRootUuid(): Uuid {
        rootUuid = rootUuid.nextInReservedBlock()
        return rootUuid
    }

    fun update(edit: (Model, IWritableGraph) -> Unit) {
        val g = graph.startWriting()
        edit(this, g)
        graph.stopReading()
        graph = g.stopWriting()
    }

    fun update(edit: (ModelUpdate) -> Unit) {

        val writeableGraph = graph.startWriting()
        val modelUpdate = ModelUpdate(this, writeableGraph)

        edit(modelUpdate)

        graph.stopReading()
        graph = writeableGraph.stopWriting()

    }

}

//---------------------------------------------------------------------------------------------------------------------

