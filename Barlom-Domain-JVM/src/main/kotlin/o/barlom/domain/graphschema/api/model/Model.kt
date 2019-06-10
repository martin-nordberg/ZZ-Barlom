//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.model

import o.barlom.domain.graphschema.api.concepts.ConceptType
import o.barlom.domain.graphschema.api.concepts.Package
import o.barlom.infrastructure.graphs.IWritableGraph
import o.barlom.infrastructure.graphs.Id
import o.barlom.infrastructure.graphs.graphOf
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

class Model(

    /** Function for creating new UUIDs */
    val makeUuid: () -> Uuid,

    /** The unique ID for the root package within this model. */
    val rootPackageId: Id<Package> = Id(Uuid.fromString("66522300-6c7d-11e7-81b7-080027b6d283")),

    /** The unique ID for the root concept type within this model. */
    val rootConceptTypeId: Id<ConceptType> = Id(Uuid.fromString("66522301-6c7d-11e7-81b7-080027b6d283")),

    /** The unique ID for the root directed edge type within this model. */
    val rootUndirectedEdgeTypeId: Uuid = Uuid.fromString("66522302-6c7d-11e7-81b7-080027b6d283"),

    /** The unique ID for the root undirected edge type within this model. */
    val rootDirectedEdgeTypeId: Uuid = Uuid.fromString("66522303-6c7d-11e7-81b7-080027b6d283")

) {

    val rootConceptType = ConceptType(rootConceptTypeId, true)

    val rootPackage = Package(rootPackageId, true)

    var graph = graphOf().update {
        addConcept(rootPackage)
        addConcept(rootConceptType)
    }

    ////

    fun <T> makeId(): Id<T> =
        Id(makeUuid())

    fun update(edit: (Model, IWritableGraph) -> Unit) {
        val g = graph.startWriting()
        edit(this, g)
        graph.stopReading()
        graph = g.stopWriting()
    }
}


//---------------------------------------------------------------------------------------------------------------------

