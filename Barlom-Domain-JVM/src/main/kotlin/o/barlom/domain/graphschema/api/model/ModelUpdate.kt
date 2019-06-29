//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.model

import o.barlom.domain.graphschema.api.concepts.ConceptType
import o.barlom.domain.graphschema.api.concepts.DirectedConnectionType
import o.barlom.domain.graphschema.api.concepts.Module
import o.barlom.domain.graphschema.api.connections.ConceptTypeContainment
import o.barlom.domain.graphschema.api.connections.Containment
import o.barlom.domain.graphschema.api.connections.DirectedConnectionTypeContainment
import o.barlom.domain.graphschema.api.connections.ModuleContainment
import o.barlom.domain.graphschema.api.types.*
import o.barlom.infrastructure.graphs.IWritableGraph
import o.barlom.infrastructure.graphs.Id
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

class ModelUpdate(

    val model: Model,

    override val graph: IWritableGraph

) : IModel by model {

    fun conceptType(
        name: String = "NewConceptType",
        description: String = "",
        abstractness: EAbstractness = EAbstractness.CONCRETE,
        uuid: Uuid = makeUuid()
    ) =
        graph.addConcept(ConceptType(uuid, false, name, description, abstractness))

    fun ConceptType.containedBy(
        parentModule: Id<Module>,
        sharing: ESharing = ESharing.SHARED,
        uuid: Uuid = makeUuid()
    ): ConceptTypeContainment =
        graph.addConnection(
            ConceptTypeContainment(
                Containment.CONCEPT_TYPE_CONTAINMENT_TYPE,
                uuid,
                parentModule,
                this,
                sharing
            )
        )

    fun DirectedConnectionType.containedBy(
        parentModule: Id<Module>,
        sharing: ESharing = ESharing.SHARED,
        uuid: Uuid = makeUuid()
    ): DirectedConnectionTypeContainment =
        graph.addConnection(
            DirectedConnectionTypeContainment(
                Containment.DIRECTED_CONNECTION_TYPE_CONTAINMENT_TYPE,
                uuid,
                parentModule,
                this,
                sharing
            )
        )

    fun Module.containedBy(
        parentModule: Id<Module>,
        sharing: ESharing = ESharing.SHARED,
        uuid: Uuid = makeUuid()
    ): ModuleContainment =
        graph.addConnection(
            ModuleContainment(
                Containment.MODULE_CONTAINMENT_TYPE,
                uuid,
                parentModule,
                this,
                sharing
            )
        )

    fun Module.contains(
        child: ConceptType,
        sharing: ESharing = ESharing.SHARED,
        uuid: Uuid = makeUuid()
    ): ConceptTypeContainment =
        graph.addConnection(
            ConceptTypeContainment(
                Containment.CONCEPT_TYPE_CONTAINMENT_TYPE,
                uuid,
                this,
                child,
                sharing
            )
        )

    fun Module.contains(
        child: DirectedConnectionType,
        sharing: ESharing = ESharing.SHARED,
        uuid: Uuid = makeUuid()
    ): DirectedConnectionTypeContainment =
        graph.addConnection(
            DirectedConnectionTypeContainment(
                Containment.DIRECTED_CONNECTION_TYPE_CONTAINMENT_TYPE,
                uuid,
                this,
                child,
                sharing
            )
        )

    fun Module.contains(
        childModule: Module,
        sharing: ESharing = ESharing.SHARED,
        uuid: Uuid = makeUuid()
    ): ModuleContainment =
        graph.addConnection(
            ModuleContainment(
                Containment.MODULE_CONTAINMENT_TYPE,
                uuid,
                this,
                childModule,
                sharing
            )
        )


    fun directedConnectionType(
        name: String = "NewDirectedConnectionType",
        description: String = "",
        abstractness: EAbstractness = EAbstractness.CONCRETE,
        cyclicity: ECyclicity = ECyclicity.DEFAULT,
        multiEdgedness: EMultiEdgedness = EMultiEdgedness.DEFAULT,
        selfLooping: ESelfLooping = ESelfLooping.DEFAULT,
        forwardName: String? = null,
        headRoleName: String? = null,
        maxHeadInDegree: Int? = null,
        maxTailOutDegree: Int? = null,
        minHeadInDegree: Int = 0,
        minTailOutDegree: Int = 0,
        reverseName: String? = null,
        tailRoleName: String? = null,
        uuid: Uuid = makeUuid()
    ) =
        graph.addConcept(DirectedConnectionType(
            uuid, false, name, description, abstractness, cyclicity, multiEdgedness, selfLooping,
            forwardName, headRoleName, maxHeadInDegree, maxTailOutDegree, minHeadInDegree, minTailOutDegree,
            reverseName, tailRoleName))

    fun module(
        name: String = "newmodule",
        description: String = "",
        uuid: Uuid = makeUuid()
    ) =
        graph.addConcept(Module(uuid, false, name, description))

}

//---------------------------------------------------------------------------------------------------------------------

