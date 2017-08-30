//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices2

import org.barlom.domain.metamodel.api.edges2.VertexTypeContainment
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.infrastructure.revisions.V
import org.barlom.infrastructure.revisions.VLinkedList
import org.barlom.infrastructure.uuids.Uuid

class VertexType(

    override val id: Uuid,
    val isRoot: Boolean

) : AbstractPackagedElement() {

    private val _abstractness = V(if (isRoot) EAbstractness.ABSTRACT else EAbstractness.CONCRETE)
    private val _name = V("newvertextype")
    private val _vertexTypeContainments = VLinkedList<VertexTypeContainment>()


    /** Whether this vertex type is abstract. */
    var abstractness: EAbstractness
        get() = _abstractness.get()
        set(value) {

            check(!isRoot) {
                "Root vertex type abstractness cannot be changed"
            }

            _abstractness.set(value)

        }

    override var name: String
        get() = _name.get()
        set(value) {

            check(!isRoot) {
                "Root vertex type name cannot be changed"
            }

            _name.set(value)

        }

    override val parentPackages: List<Package>
        get() = _vertexTypeContainments.map { c -> c.parent }.sortedBy { pkg -> pkg.name }

    override val path: String
        get() {

            if (_vertexTypeContainments.isEmpty) {
                return name
            }

            val parentPath = parentPackages[0].path

            if (parentPath.isEmpty()) {
                return name
            }

            return parentPath + "." + name

        }

    /** Link to the package containing this vertex type. */
    val vertexTypeContainments: List<VertexTypeContainment>
        get() = _vertexTypeContainments.sortedBy { c -> c.parent.name }


    /** Adds a vertex type to this, its parent package's, list of vertex type containments. */
    internal fun addVertexTypeContainment(vertexTypeContainment: VertexTypeContainment) {

        require(vertexTypeContainment.child === this) {
            "Cannot add a package to a vertex type not its child."
        }

        _vertexTypeContainments.add(vertexTypeContainment)

    }

}