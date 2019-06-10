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
import o.barlom.infrastructure.graphs.Id
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
    val rootPackageId: Id<Package> = Id(nextRootUuid())

    /** The unique ID for the root concept type within this model. */
    val rootConceptTypeId: Id<ConceptType> = Id(nextRootUuid())

    /** The unique ID for the root directed connection type within this model. */
    val rootUndirectedConnectionTypeId: Id<UndirectedConnectionType> = Id(nextRootUuid())

    /** The unique ID for the root undirected connection type within this model. */
    val rootDirectedConnectionTypeId: Id<DirectedConnectionType> = Id(nextRootUuid())

    val rootConceptType = ConceptType(rootConceptTypeId, true)

    val rootDirectedConnectionType = DirectedConnectionType(rootDirectedConnectionTypeId, true)

    val rootPackage = Package(rootPackageId, true)

    val rootUndirectedConnectionType = UndirectedConnectionType(rootUndirectedConnectionTypeId, true)

    ////

    var graph = graphOf().update {
        addConcept(rootPackage)
        addConcept(rootConceptType)
        addConcept(rootDirectedConnectionType)
        addConcept(rootUndirectedConnectionType)

        addConnection(ConceptTypeContainment(Id(nextRootUuid()), rootPackageId, rootConceptTypeId))
        addConnection(DirectedConnectionTypeContainment(Id(nextRootUuid()), rootPackageId, rootDirectedConnectionTypeId))
        addConnection(UndirectedConnectionTypeContainment(Id(nextRootUuid()), rootPackageId, rootUndirectedConnectionTypeId))
    }

    ////

    fun <T> makeId(): Id<T> =
        Id(makeUuid())

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

