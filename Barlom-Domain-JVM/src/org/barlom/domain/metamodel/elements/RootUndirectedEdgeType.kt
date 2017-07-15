//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements;

import org.barlom.domain.metamodel.api.elements.IUndirectedEdgeType
import org.barlom.domain.metamodel.api.elements.IVertexType
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.types.ECyclicity
import org.barlom.domain.metamodel.api.types.EMultiEdgedness
import org.barlom.domain.metamodel.api.types.ESelfLooping

/**
 * Implementation of the top-level root directed edge type.
 */
class RootUndirectedEdgeType(

    override val id: String,

    override val parentPackage : RootPackage,

    private val _rootVertexType: RootVertexType

) : IUndirectedEdgeType {

    override val name: String
        get() = "UndirectedEdgeType"

    override val maxDegree: Int?
        get() = null

    override val minDegree: Int?
        get() = null

    override val superType: IUndirectedEdgeType
        get() = this

    override val abstractness: EAbstractness
        get() = EAbstractness.ABSTRACT

    override val vertexType: IVertexType
        get() = _rootVertexType

    override fun isSubTypeOf(edgeType: IUndirectedEdgeType): Boolean {
        return edgeType === this
    }

    override val cyclicity: ECyclicity
        get() = TODO(
            "not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val multiEdgedness: EMultiEdgedness
        get() = TODO(
            "not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val selfLooping: ESelfLooping
        get() = TODO(
            "not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override val attributes: List<EdgeAttributeDecl>
        get() = listOf()

}
