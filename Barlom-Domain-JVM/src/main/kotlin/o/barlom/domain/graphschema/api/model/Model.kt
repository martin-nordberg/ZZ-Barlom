//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.model

import o.barlom.domain.graphschema.api.concepts.ConceptType
import o.barlom.domain.graphschema.api.concepts.DirectedConnectionType
import o.barlom.domain.graphschema.api.concepts.Package
import o.barlom.domain.graphschema.api.concepts.UndirectedConnectionType
import o.barlom.domain.graphschema.api.connections.ConceptTypeContainment
import o.barlom.domain.graphschema.api.connections.DirectedConnectionTypeContainment
import o.barlom.domain.graphschema.api.connections.UndirectedConnectionTypeContainment
import o.barlom.infrastructure.graphs.IWritableGraph
import o.barlom.infrastructure.graphs.graphOf
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

class Model(

    /** Function for creating new UUIDs */
    val makeUuid: () -> Uuid,

    /** The unique ID for the root package within this model. */
    val modelUuid: Uuid = Uuid.fromString("66522300-6c7d-11e7-81b7-080027b6d283")

) {

    var rootUuid = modelUuid

    /** The unique ID for the root package within this model. */
    private val rootPackageUuid = nextRootUuid()

    /** The unique ID for the root concept type within this model. */
    private val rootConceptTypeUuid = nextRootUuid()

    /** The unique ID for the root directed connection type within this model. */
    private val rootUndirectedConnectionTypeUuid = nextRootUuid()

    /** The unique ID for the root undirected connection type within this model. */
    private val rootDirectedConnectionTypeUuid = nextRootUuid()

    val rootConceptType = ConceptType(rootConceptTypeUuid, true)

    val rootDirectedConnectionType = DirectedConnectionType(rootDirectedConnectionTypeUuid, true)

    val rootPackage = Package(rootPackageUuid, true)

    val rootUndirectedConnectionType = UndirectedConnectionType(rootUndirectedConnectionTypeUuid, true)

    ////

    var graph = graphOf().update {
        addConcept(rootPackage)
        addConcept(rootConceptType)
        addConcept(rootDirectedConnectionType)
        addConcept(rootUndirectedConnectionType)

        addConnection(ConceptTypeContainment(nextRootUuid(), rootPackage, rootConceptType))
        addConnection(DirectedConnectionTypeContainment(nextRootUuid(), rootPackage, rootDirectedConnectionType))
        addConnection(UndirectedConnectionTypeContainment(nextRootUuid(), rootPackage, rootUndirectedConnectionType))
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

}

//---------------------------------------------------------------------------------------------------------------------

