//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices

import org.barlom.domain.metamodel.api.edges.EdgeAttributeTypeContainment
import org.barlom.domain.metamodel.api.edges.UndirectedEdgeTypeConnectivity
import org.barlom.domain.metamodel.api.edges.UndirectedEdgeTypeContainment
import org.barlom.domain.metamodel.api.edges.UndirectedEdgeTypeInheritance
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.types.ECyclicity
import org.barlom.domain.metamodel.api.types.EMultiEdgedness
import org.barlom.domain.metamodel.api.types.ESelfLooping
import org.barlom.infrastructure.revisions.V
import org.barlom.infrastructure.revisions.VLinkedList
import org.barlom.infrastructure.uuids.Uuid

/**
 * Implementation class for undirected edge types.
 */
class UndirectedEdgeType(

    override val id: Uuid,
    override val isRoot: Boolean

) : AbstractEdgeType() {

    private val _abstractness = if (isRoot) V(EAbstractness.ABSTRACT) else V(EAbstractness.CONCRETE)
    private val _connectivities = VLinkedList<UndirectedEdgeTypeConnectivity>()
    private val _cyclicity = V(ECyclicity.DEFAULT)
    private val _description = if (isRoot) V("Root undirected edge type.") else V("")
    private val _edgeAttributeTypeContainments = VLinkedList<EdgeAttributeTypeContainment>()
    private val _maxDegree: V<Int?> = V(null)
    private val _minDegree: V<Int?> = V(null)
    private val _multiEdgedness = V(EMultiEdgedness.DEFAULT)
    private val _name = if (isRoot) V("RootUndirectedEdgeType") else V("NewUndirectedEdgeType")
    private val _selfLooping = V(ESelfLooping.DEFAULT)
    private val _subTypeUndirectedEdgeTypeInheritances = VLinkedList<UndirectedEdgeTypeInheritance>()
    private val _superTypeUndirectedEdgeTypeInheritances = VLinkedList<UndirectedEdgeTypeInheritance>()
    private val _undirectedEdgeTypeContainments = VLinkedList<UndirectedEdgeTypeContainment>()


    override var abstractness: EAbstractness
        get() = _abstractness.get()
        set(value) {

            check(!isRoot) {
                "Root undirected edge type abstractness cannot be changed"
            }

            _abstractness.set(value)

        }

    override val attributeTypes: List<EdgeAttributeType>
        get() = _edgeAttributeTypeContainments.map { c -> c.attributeType }.sortedBy { at -> at.name }

    /** The vertex type connected to this edge type. */
    val connectedVertexTypes: List<VertexType>
        get() = _connectivities.map { c -> c.connectedVertexType }.sortedBy { vt -> vt.name }

    /** The connectivities to the vertex type connected to this edge type. */
    val connectivities: List<UndirectedEdgeTypeConnectivity>
        get() = _connectivities.sortedBy { c -> c.connectedVertexType.path }

    override var cyclicity: ECyclicity
        get() = _cyclicity.get()
        set(value) {

            check(!isRoot) {
                "Root undirected edge type cyclicity cannot be changed"
            }

            _cyclicity.set(value)

        }

    override var description: String
        get() = _description.get()
        set(value) {


            check(!isRoot) {
                "Root undirected edge type description cannot be changed"
            }

            _description.set(value)

        }

    override var multiEdgedness: EMultiEdgedness
        get() = _multiEdgedness.get()
        set(value) {

            check(!isRoot) {
                "Root undirected edge type multiEdgedness cannot be changed"
            }

            _multiEdgedness.set(value)

        }

    /** The maximum degree for the vertices connected by edges of this type. */
    var maxDegree: Int?
        get() = _maxDegree.get()
        set(value) {

            check(!isRoot) {
                "Root undirected edge type maxDegree cannot be changed"
            }

            _maxDegree.set(value)

        }

    /** The minimum degree for the vertices connected by edges of this type. */
    var minDegree: Int?
        get() = _minDegree.get()
        set(value) {

            check(!isRoot) {
                "Root undirected edge type maxDegree cannot be changed"
            }

            _minDegree.set(value)

        }

    override var name: String
        get() = _name.get()
        set(value) {

            check(!isRoot) {
                "Root undirected edge type name cannot be changed"
            }

            _name.set(value)

        }

    override val parents: List<Package>
        get() = _undirectedEdgeTypeContainments.map { c -> c.parent }.sortedBy { pkg -> pkg.name }

    override val path: String
        get() {

            if (_undirectedEdgeTypeContainments.isEmpty()) {
                return name
            }

            val parentPath = parents[0].path

            if (parentPath.isEmpty()) {
                return name
            }

            return parentPath + "." + name

        }

    override var selfLooping: ESelfLooping
        get() = _selfLooping.get()
        set(value) {

            check(!isRoot) {
                "Root undirected edge type selfLooping cannot be changed"
            }

            _selfLooping.set(value)

        }

    /** The sub types of this edge type. */
    val subTypes: List<UndirectedEdgeType>
        get() = _subTypeUndirectedEdgeTypeInheritances.map { i -> i.subType }.sortedBy { et -> et.path }

    /** The sub types of this edge type. */
    val subTypeInheritances: List<UndirectedEdgeTypeInheritance>
        get() = _subTypeUndirectedEdgeTypeInheritances.sortedBy { et -> et.subType.path }

    /** The super types of this edge type. */
    val superTypes: List<UndirectedEdgeType>
        get() = _superTypeUndirectedEdgeTypeInheritances.map { i -> i.superType }.sortedBy { et -> et.path }

    /** The super types of this edge type. */
    val superTypeInheritances: List<UndirectedEdgeTypeInheritance>
        get() = _superTypeUndirectedEdgeTypeInheritances.sortedBy { et -> et.superType.path }


    /** Adds an attribute type to this, its parent edge type's, list of edge attribute type containments. */
    override fun addEdgeAttributeTypeContainment(edgeAttributeTypeContainment: EdgeAttributeTypeContainment) {

        require(edgeAttributeTypeContainment.edgeType === this) {
            "Cannot link an edge attribute type to an edge type not its container."
        }

        _edgeAttributeTypeContainments.add(edgeAttributeTypeContainment)

    }

    /** Adds a sub undirected edge type to this, its super type's, list of undirected edge type inheritances. */
    internal fun addSubTypeUndirectedEdgeTypeInheritance(undirectedEdgeTypeInheritance: UndirectedEdgeTypeInheritance) {

        require(undirectedEdgeTypeInheritance.superType === this) {
            "Cannot add a sub type to an undirected edge type not its super type."
        }

        _subTypeUndirectedEdgeTypeInheritances.add(undirectedEdgeTypeInheritance)

    }

    /** Adds a super undirected edge type to this, its super type's, list of undirected edge type inheritances. */
    internal fun addSuperTypeUndirectedEdgeTypeInheritance(
        undirectedEdgeTypeInheritance: UndirectedEdgeTypeInheritance) {

        require(undirectedEdgeTypeInheritance.subType === this) {
            "Cannot add a super type to an undirected edge type not its sub type."
        }

        _superTypeUndirectedEdgeTypeInheritances.add(undirectedEdgeTypeInheritance)

    }

    /** Adds an vertex type to this, its connecting edge type's, list of undirected edge type connectivities. */
    internal fun addUndirectedEdgeTypeConnectivity(undirectedEdgeTypeConnectivity: UndirectedEdgeTypeConnectivity) {

        require(undirectedEdgeTypeConnectivity.connectingEdgeType === this) {
            "Cannot link a vertex type to an undirected edge type not its connector."
        }

        _connectivities.add(undirectedEdgeTypeConnectivity)

    }

    /** Adds a package to this, its child edge type's, list of undirected edge type containments. */
    internal fun addUndirectedEdgeTypeContainment(undirectedEdgeTypeContainment: UndirectedEdgeTypeContainment) {

        require(undirectedEdgeTypeContainment.child === this) {
            "Cannot link a package to an undirected edge type not its child."
        }

        _undirectedEdgeTypeContainments.add(undirectedEdgeTypeContainment)

    }

    /** Finds the vertex types that are eligible to be connected by this edge type. */
    fun findPotentialConnectedVertexTypes(): List<VertexType> {

        val result = mutableListOf<VertexType>()

        val rootPackage = parents[0].findRootPackage()

        for (pkg in parents) {

            // same package as parent vertex type
            for (vt in pkg.vertexTypes) {
                result.add(vt)
            }

            for (pkg2 in pkg.transitiveSuppliers) {

                for (vt in pkg2.vertexTypes) {
                    result.add(vt)
                }

            }

            for (vt in rootPackage.vertexTypes) {
                result.add(vt)
            }

        }

        return result

    }

    /** Finds the edge types that are eligible to be the super type of this edge type. */
    fun findPotentialSuperTypes(): List<UndirectedEdgeType> {

        val result = mutableListOf<UndirectedEdgeType>()

        val rootPackage = parents[0].findRootPackage()

        for (pkg in parents) {

            // same package as parent vertex type
            for (et in pkg.undirectedEdgeTypes) {
                if (et !== this && !et.hasSuperType(this)) {
                    result.add(et)
                }
            }

            for (pkg2 in pkg.transitiveSuppliers) {

                for (et in pkg2.undirectedEdgeTypes) {
                    if (!et.hasSuperType(this)) {
                        result.add(et)
                    }
                }

            }

            for (et in rootPackage.undirectedEdgeTypes) {
                result.add(et)
            }

        }

        return result

    }

    override fun hasParent(pkg: Package): Boolean {

        var result = false

        _undirectedEdgeTypeContainments.forEachWhile { edgeTypeContainment ->

            result = pkg === edgeTypeContainment.parent
            !result

        }

        return result

    }

    override fun hasParentPackage(pkg: Package) = hasParent(pkg)

    /** Whether the given [undirectedEdgeType] is a direct sub type of this one. */
    fun hasSubType(undirectedEdgeType: UndirectedEdgeType): Boolean {

        var result = false

        _subTypeUndirectedEdgeTypeInheritances.forEachWhile { undirectedEdgeTypeInheritance ->

            result = undirectedEdgeType === undirectedEdgeTypeInheritance.subType
            !result

        }

        return result

    }

    /** Whether the given [undirectedEdgeType] is a direct super type of this one. */
    fun hasSuperType(undirectedEdgeType: UndirectedEdgeType): Boolean {

        var result = false

        _superTypeUndirectedEdgeTypeInheritances.forEachWhile { undirectedEdgeTypeInheritance ->

            result = undirectedEdgeType === undirectedEdgeTypeInheritance.superType
            !result

        }

        return result

    }

    /** Whether the given [undirectedEdgeType] is a direct or indirect sub type of this one. */
    fun hasTransitiveSubType(undirectedEdgeType: UndirectedEdgeType): Boolean {

        val subTypes: MutableSet<UndirectedEdgeType> = mutableSetOf()

        // Helper function recursively searches while accumulating the undirectedEdge types searched so far
        fun findSubType(superType: UndirectedEdgeType): Boolean {

            for (undirectedEdgeTypeInheritance in superType._subTypeUndirectedEdgeTypeInheritances.asList()) {

                val subType = undirectedEdgeTypeInheritance.subType

                if (subType === undirectedEdgeType) {
                    return true
                }

                if (!subTypes.contains(subType)) {

                    subTypes.add(subType)

                    if (findSubType(subType)) {
                        return true
                    }

                }

            }

            return false

        }

        return findSubType(this)

    }

    /** Whether the given [undirectedEdgeType] is a direct or indirect super type of this one. */
    fun hasTransitiveSuperType(undirectedEdgeType: UndirectedEdgeType): Boolean {

        val superTypes: MutableSet<UndirectedEdgeType> = mutableSetOf()

        // Helper function recursively searches while accumulating the undirectedEdge types searched so far
        fun findSuperType(subType: UndirectedEdgeType): Boolean {

            for (undirectedEdgeTypeInheritance in subType._superTypeUndirectedEdgeTypeInheritances.asList()) {

                val superType = undirectedEdgeTypeInheritance.superType

                if (superType === undirectedEdgeType) {
                    return true
                }

                if (!superTypes.contains(superType)) {

                    superTypes.add(superType)

                    if (findSuperType(superType)) {
                        return true
                    }

                }

            }

            return false

        }

        return findSuperType(this)

    }

    override fun remove() {
        _edgeAttributeTypeContainments.forEach { c -> c.remove() }
        _subTypeUndirectedEdgeTypeInheritances.forEach { i -> i.remove() }
        _superTypeUndirectedEdgeTypeInheritances.forEach { i -> i.remove() }
        _connectivities.forEach { c -> c.remove() }
        _undirectedEdgeTypeContainments.forEach { c -> c.remove() }
    }

    /** Removes an attribute type from this, its parent edge type's, list of edge attribute type containments. */
    override fun removeEdgeAttributeTypeContainment(edgeAttributeTypeContainment: EdgeAttributeTypeContainment) {

        require(_edgeAttributeTypeContainments.remove(edgeAttributeTypeContainment)) {
            "Cannot unlink an edge attribute type from an edge type not its container."
        }

    }

    /** Removes a sub undirected edge type to this, its super type's, list of undirected edge type inheritances. */
    internal fun removeSubTypeUndirectedEdgeTypeInheritance(
        undirectedEdgeTypeInheritance: UndirectedEdgeTypeInheritance) {

        require(_subTypeUndirectedEdgeTypeInheritances.remove(undirectedEdgeTypeInheritance)) {
            "Cannot remove a sub type from an undirected edge type not its super type."
        }

    }

    /** Removes a super undirected edge type from this, its super type's, list of undirected edge type inheritances. */
    internal fun removeSuperTypeUndirectedEdgeTypeInheritance(
        undirectedEdgeTypeInheritance: UndirectedEdgeTypeInheritance) {

        require(_superTypeUndirectedEdgeTypeInheritances.remove(undirectedEdgeTypeInheritance)) {
            "Cannot remove a super type from an undirected edge type not its super type."
        }

    }

    /** Removes a vertex type from this, its connecting edge type's, list of undirected edge type connectivities. */
    internal fun removeUndirectedEdgeTypeConnectivity(undirectedEdgeTypeConnectivity: UndirectedEdgeTypeConnectivity) {

        require(_connectivities.remove(undirectedEdgeTypeConnectivity)) {
            "Cannot unlink a vertex type from an undirected edge type not its connector."
        }

    }

    /** Removes a package from this, its child edge type's, list of undirected edge type containments. */
    internal fun removeUndirectedEdgeTypeContainment(undirectedEdgeTypeContainment: UndirectedEdgeTypeContainment) {

        require(_undirectedEdgeTypeContainments.remove(undirectedEdgeTypeContainment)) {
            "Cannot unlink a package from an undirected edge type not its child."
        }

    }

}