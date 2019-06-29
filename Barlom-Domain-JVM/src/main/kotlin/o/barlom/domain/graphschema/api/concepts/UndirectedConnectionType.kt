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
 * Class representing the metadata for an undirected connection type.
 */
data class UndirectedConnectionType(
    override val uuid: Uuid,
    override val isRoot: Boolean = false,
    override val name: String = if (isRoot) "RootUndirectedConnectionType" else "NewConnectionType",
    override val description: String = if (isRoot) "Root undirected connection type." else "",
    override val abstractness: EAbstractness = EAbstractness.fromBoolean(isRoot),
    override val cyclicity: ECyclicity = ECyclicity.DEFAULT,
    override val multiEdgedness: EMultiEdgedness = EMultiEdgedness.DEFAULT,
    override val selfLooping: ESelfLooping = ESelfLooping.DEFAULT,
    val maxDegree: Int? = null,
    val minDegree: Int = 0
) : AbstractConnectionType<UndirectedConnectionType>() {

    override fun get(propertyName: String): Any? =
        when (propertyName) {
            "maxDegree" -> maxDegree
            "minDegree" -> minDegree
            else        -> super.get(propertyName)
        }

    override fun hasProperty(propertyName: String): Boolean =
        when (propertyName) {
            "maxDegree" -> true
            "minDegree" -> true
            else        -> super.hasProperty(propertyName)
        }

    override fun propertyNames(): Set<String> =
        super.propertyNames().plus("maxDegree").plus("minDegree")

    override val typeId = TYPE_ID

    ////

    companion object {
        val TYPE_ID = ConceptTypeId<UndirectedConnectionType>(
            "o.barlom.domain.graphschema.api.concepts.UndirectedConnectionType"
        )
    }

}

//---------------------------------------------------------------------------------------------------------------------

