//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.concepts

import o.barlom.domain.graphschema.api.types.EAbstractness
import o.barlom.domain.graphschema.api.types.ECyclicity
import o.barlom.domain.graphschema.api.types.EMultiEdgedness
import o.barlom.domain.graphschema.api.types.ESelfLooping
import o.barlom.infrastructure.graphs.ConceptTypeId
import x.barlom.infrastructure.uuids.Uuid

//---------------------------------------------------------------------------------------------------------------------

/**
 * The type of a directed connection (edge or link).
 */
data class DirectedConnectionType(
    override val uuid: Uuid,
    override val isRoot: Boolean = false,
    override val name: String = if (isRoot) "RootDirectedConnectionType" else "NewDirectedConnectionType",
    override val description: String = if (isRoot) "Root directed edge type." else "",
    override val abstractness: EAbstractness = EAbstractness.fromBoolean(isRoot),
    override val cyclicity: ECyclicity = ECyclicity.DEFAULT,
    override val multiEdgedness: EMultiEdgedness = EMultiEdgedness.DEFAULT,
    override val selfLooping: ESelfLooping = ESelfLooping.DEFAULT,
    val forwardName: String? = null,
    val headRoleName: String? = null,
    val maxHeadInDegree: Int? = null,
    val maxTailOutDegree: Int? = null,
    val minHeadInDegree: Int = 0,
    val minTailOutDegree: Int = 0,
    val reverseName: String? = null,
    val tailRoleName: String? = null
) : AbstractConnectionType<DirectedConnectionType>() {

    override fun get(propertyName: String): Any? =
        when (propertyName) {
            "forwardName"      -> forwardName
            "headRoleName"     -> headRoleName
            "maxHeadInDegree"  -> maxHeadInDegree
            "maxTailOutDegree" -> maxTailOutDegree
            "minHeadInDegree"  -> minHeadInDegree
            "minTailOutDegree" -> minTailOutDegree
            "reverseName"      -> reverseName
            "tailRoleName"     -> tailRoleName
            else               -> super.get(propertyName)
        }

    override fun hasProperty(propertyName: String): Boolean =
        when (propertyName) {
            "forwardName"      -> true
            "headRoleName"     -> true
            "maxHeadInDegree"  -> true
            "maxTailOutDegree" -> true
            "minHeadInDegree"  -> true
            "minTailOutDegree" -> true
            "reverseName"      -> true
            "tailRoleName"     -> true
            else               -> super.hasProperty(propertyName)
        }

    override fun propertyNames(): Set<String> =
        super.propertyNames()
            .plus("forwardName")
            .plus("headRoleName")
            .plus("maxHeadInDegree")
            .plus("maxTailOutDegree")
            .plus("minHeadInDegree")
            .plus("minTailOutDegree")
            .plus("reverseName")
            .plus("tailRoleName")

    override val typeId = TYPE_ID

    ////

    companion object {
        val TYPE_ID = ConceptTypeId<DirectedConnectionType>(
            "o.barlom.domain.graphschema.api.concepts.DirectedConnectionType"
        )
    }

}

//---------------------------------------------------------------------------------------------------------------------

