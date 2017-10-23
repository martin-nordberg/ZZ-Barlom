//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.vertices

import org.barlom.domain.metamodel.api.edges.*
import org.barlom.domain.metamodel.api.types.EAbstractness
import org.barlom.domain.metamodel.api.types.ECyclicity
import org.barlom.domain.metamodel.api.types.EMultiEdgedness
import org.barlom.domain.metamodel.api.types.ESelfLooping
import org.barlom.infrastructure.revisions.V
import org.barlom.infrastructure.revisions.VLinkedList
import org.barlom.infrastructure.uuids.Uuid

/**
 * Implementation class for directed edge types.
 */
class DirectedEdgeType(

    override val id: Uuid,
    override val isRoot: Boolean

) : AbstractEdgeType() {

    private val _abstractness = V(if (isRoot) EAbstractness.ABSTRACT else EAbstractness.CONCRETE)
    private val _cyclicity = V(ECyclicity.UNCONSTRAINED)
    private val _directedEdgeTypeContainments = VLinkedList<DirectedEdgeTypeContainment>()
    private val _directedEdgeTypeHeadConnectivities = VLinkedList<DirectedEdgeTypeHeadConnectivity>()
    private val _directedEdgeTypeTailConnectivities = VLinkedList<DirectedEdgeTypeTailConnectivity>()
    private val _edgeAttributeTypeContainments = VLinkedList<EdgeAttributeTypeContainment>()
    private val _forwardName: V<String?> = V(null)
    private val _headRoleName: V<String?> = V(null)
    private val _maxHeadInDegree: V<Int?> = V(null)
    private val _maxTailOutDegree: V<Int?> = V(null)
    private val _minHeadInDegree: V<Int?> = V(null)
    private val _minTailOutDegree: V<Int?> = V(null)
    private val _multiEdgedness = V(EMultiEdgedness.UNCONSTRAINED)
    private val _name = V("NewUndirectedEdgeType")
    private val _selfLooping = V(ESelfLooping.UNCONSTRAINED)
    private val _subTypeDirectedEdgeTypeInheritances = VLinkedList<DirectedEdgeTypeInheritance>()
    private val _superTypeDirectedEdgeTypeInheritances = VLinkedList<DirectedEdgeTypeInheritance>()
    private val _reverseName: V<String?> = V(null)
    private val _tailRoleName: V<String?> = V(null)


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

    /** The vertex type connected to the head of this edge type. */
    val connectedHeadVertexTypes: List<VertexType>
        get() = _directedEdgeTypeHeadConnectivities.map { c -> c.connectedVertexType }.sortedBy { vt -> vt.name }

    /** The vertex type connected to the tail of this edge type. */
    val connectedTailVertexTypes: List<VertexType>
        get() = _directedEdgeTypeTailConnectivities.map { c -> c.connectedVertexType }.sortedBy { vt -> vt.name }

    override var cyclicity: ECyclicity
        get() = _cyclicity.get()
        set(value) {

            check(!isRoot) {
                "Root undirected edge type cyclicity cannot be changed"
            }

            _cyclicity.set(value)

        }

    /** The name of this edge type when considered from tail to head. */
    var forwardName: String?
        get() = _forwardName.get()
        set(value) {

            check(!isRoot) {
                "Root directed edge type forward name cannot be changed"
            }

            _forwardName.set(value)

        }

    /** The name of the role for the vertex at the head of edges of this type. */
    var headRoleName: String?
        get() = _headRoleName.get()
        set(value) {

            check(!isRoot) {
                "Root directed edge type head role name cannot be changed"
            }

            _headRoleName.set(value)

        }

//    /** The vertex type at the head of edges of this type. */
//    val headVertexType: IVertexType

    /** The maximum in-degree for the head vertex of edges of this type. */
    var maxHeadInDegree: Int?
        get() = _maxHeadInDegree.get()
        set(value) {

            check(!isRoot) {
                "Root directed edge type max head in-degree cannot be changed"
            }

            _maxHeadInDegree.set(value)

        }

    /** The maximum out-degree for the tail vertex of edges of this type. */
    var maxTailOutDegree: Int?
        get() = _maxTailOutDegree.get()
        set(value) {

            check(!isRoot) {
                "Root directed edge type max tail out-degree cannot be changed"
            }

            _maxTailOutDegree.set(value)

        }

    /** The minimum in-degree for the head vertex of edges of this type. */
    var minHeadInDegree: Int?
        get() = _minHeadInDegree.get()
        set(value) {

            check(!isRoot) {
                "Root directed edge type min head in-degree cannot be changed"
            }

            _minHeadInDegree.set(value)

        }

    /** The minimum out-degree for the tail vertex of edges of this type. */
    var minTailOutDegree: Int?
        get() = _minTailOutDegree.get()
        set(value) {

            check(!isRoot) {
                "Root directed edge type min tail out-degree cannot be changed"
            }

            _minTailOutDegree.set(value)

        }

    override var multiEdgedness: EMultiEdgedness
        get() = _multiEdgedness.get()
        set(value) {

            check(!isRoot) {
                "Root undirected edge type multiEdgedness cannot be changed"
            }

            _multiEdgedness.set(value)

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
        get() = _directedEdgeTypeContainments.map { c -> c.parent }.sortedBy { pkg -> pkg.name }

    override val path: String
        get() {

            if (_directedEdgeTypeContainments.isEmpty()) {
                return name
            }

            val parentPath = parents[0].path

            if (parentPath.isEmpty()) {
                return name
            }

            return parentPath + "." + name

        }

    /** The name of this edge type when considered from head to tail (vs. the forward name from tail to head). */
    var reverseName: String?
        get() = _reverseName.get()
        set(value) {

            check(!isRoot) {
                "Root directed edge type reverse name cannot be changed"
            }

            _reverseName.set(value)

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
    val subTypes: List<DirectedEdgeType>
        get() = _subTypeDirectedEdgeTypeInheritances.map { i -> i.subType }.sortedBy { et -> et.path }

    /** The super types of this edge type. */
    val superTypes: List<DirectedEdgeType>
        get() = _superTypeDirectedEdgeTypeInheritances.map { i -> i.superType }.sortedBy { et -> et.path }

    /** The name of the role for the vertex at the tail of edges of this type. */
    var tailRoleName: String?
        get() = _tailRoleName.get()
        set(value) {

            check(!isRoot) {
                "Root directed edge type tail role name cannot be changed"
            }

            _tailRoleName.set(value)

        }


    /** Links a vertex type to this, its head-connecting edge type's, list of vertex type connectivities. */
    internal fun addDirectedEdgeTypeHeadConnectivity(
        directedEdgeTypeHeadConnectivity: DirectedEdgeTypeHeadConnectivity) {

        require(directedEdgeTypeHeadConnectivity.connectingEdgeType === this) {
            "Cannot link a vertex type to a directed edge type not its head connector."
        }

        _directedEdgeTypeHeadConnectivities.add(directedEdgeTypeHeadConnectivity)

    }

    /** Links a vertex type to this, its tail-connecting edge type's, list of vertex type connectivities. */
    internal fun addDirectedEdgeTypeTailConnectivity(
        directedEdgeTypeTailConnectivity: DirectedEdgeTypeTailConnectivity) {

        require(directedEdgeTypeTailConnectivity.connectingEdgeType === this) {
            "Cannot link a vertex type to a directed edge type not its tail connector."
        }

        _directedEdgeTypeTailConnectivities.add(directedEdgeTypeTailConnectivity)

    }

    /** Adds a package to this, its child edge type's, list of directed edge type containments. */
    internal fun addDirectedEdgeTypeContainment(directedEdgeTypeContainment: DirectedEdgeTypeContainment) {

        require(directedEdgeTypeContainment.child === this) {
            "Cannot link a package to a directed edge type not its child."
        }

        _directedEdgeTypeContainments.add(directedEdgeTypeContainment)

    }

    /** Adds an attribute type to this, its parent edge type's, list of edge attribute type containments. */
    override fun addEdgeAttributeTypeContainment(edgeAttributeTypeContainment: EdgeAttributeTypeContainment) {

        require(edgeAttributeTypeContainment.edgeType === this) {
            "Cannot link an edge attribute type to an edge type not its container."
        }

        _edgeAttributeTypeContainments.add(edgeAttributeTypeContainment)

    }

    /** Adds a sub directed edge type to this, its super type's, list of directed edge type inheritances. */
    internal fun addSubTypeDirectedEdgeTypeInheritance(directedEdgeTypeInheritance: DirectedEdgeTypeInheritance) {

        require(directedEdgeTypeInheritance.superType === this) {
            "Cannot add a sub type to an directed edge type not its super type."
        }

        _subTypeDirectedEdgeTypeInheritances.add(directedEdgeTypeInheritance)

    }

    /** Adds a super directed edge type to this, its super type's, list of directed edge type inheritances. */
    internal fun addSuperTypeDirectedEdgeTypeInheritance(
        directedEdgeTypeInheritance: DirectedEdgeTypeInheritance) {

        require(directedEdgeTypeInheritance.subType === this) {
            "Cannot add a super type to an directed edge type not its sub type."
        }

        _superTypeDirectedEdgeTypeInheritances.add(directedEdgeTypeInheritance)

    }

    override fun hasParent(pkg: Package): Boolean {

        var result = false

        _directedEdgeTypeContainments.forEachWhile { edgeTypeContainment ->

            result = pkg === edgeTypeContainment.parent
            !result

        }

        return result

    }

    /** Whether the given [directedEdgeType] is a direct sub type of this one. */
    fun hasSubType(directedEdgeType: DirectedEdgeType): Boolean {

        var result = false

        _subTypeDirectedEdgeTypeInheritances.forEachWhile { directedEdgeTypeInheritance ->

            result = directedEdgeType === directedEdgeTypeInheritance.subType
            !result

        }

        return result

    }

    /** Whether the given [directedEdgeType] is a direct super type of this one. */
    fun hasSuperType(directedEdgeType: DirectedEdgeType): Boolean {

        var result = false

        _superTypeDirectedEdgeTypeInheritances.forEachWhile { directedEdgeTypeInheritance ->

            result = directedEdgeType === directedEdgeTypeInheritance.superType
            !result

        }

        return result

    }

    /** Whether the given [directedEdgeType] is a direct or indirect sub type of this one. */
    fun hasTransitiveSubType(directedEdgeType: DirectedEdgeType): Boolean {

        val subTypes: MutableSet<DirectedEdgeType> = mutableSetOf()

        // Helper function recursively searches while accumulating the directedEdge types searched so far
        fun findSubType(superType: DirectedEdgeType): Boolean {

            for (directedEdgeTypeInheritance in superType._subTypeDirectedEdgeTypeInheritances.asList()) {

                val subType = directedEdgeTypeInheritance.subType

                if (subType === directedEdgeType) {
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

    /** Whether the given [directedEdgeType] is a direct or indirect super type of this one. */
    fun hasTransitiveSuperType(directedEdgeType: DirectedEdgeType): Boolean {

        val superTypes: MutableSet<DirectedEdgeType> = mutableSetOf()

        // Helper function recursively searches while accumulating the directedEdge types searched so far
        fun findSuperType(subType: DirectedEdgeType): Boolean {

            for (directedEdgeTypeInheritance in subType._superTypeDirectedEdgeTypeInheritances.asList()) {

                val superType = directedEdgeTypeInheritance.superType

                if (superType === directedEdgeType) {
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


}