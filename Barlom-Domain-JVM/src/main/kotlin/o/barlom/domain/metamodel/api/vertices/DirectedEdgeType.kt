//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.metamodel.api.vertices

import o.barlom.domain.metamodel.api.edges.*
import o.barlom.domain.metamodel.api.types.EAbstractness
import o.barlom.domain.metamodel.api.types.ECyclicity
import o.barlom.domain.metamodel.api.types.EMultiEdgedness
import o.barlom.domain.metamodel.api.types.ESelfLooping
import o.barlom.infrastructure.revisions.V
import o.barlom.infrastructure.revisions.VLinkedList
import x.barlom.infrastructure.uuids.Uuid

/**
 * Implementation class for directed edge types.
 */
class DirectedEdgeType(

    override val id: Uuid,
    override val isRoot: Boolean

) : AbstractEdgeType() {

    private val _abstractness = if (isRoot) V(EAbstractness.ABSTRACT) else V(EAbstractness.CONCRETE)
    private val _cyclicity = V(ECyclicity.DEFAULT)
    private val _description = if (isRoot) V("Root directed edge type.") else V("")
    private val _directedEdgeTypeContainments = VLinkedList<DirectedEdgeTypeContainment>()
    private val _edgeAttributeTypeContainments = VLinkedList<EdgeAttributeTypeContainment>()
    private val _forwardName: V<String?> = V(null)
    private val _headConnectivities = VLinkedList<DirectedEdgeTypeHeadConnectivity>()
    private val _headRoleName: V<String?> = V(null)
    private val _maxHeadInDegree: V<Int?> = V(null)
    private val _maxTailOutDegree: V<Int?> = V(null)
    private val _minHeadInDegree: V<Int?> = V(null)
    private val _minTailOutDegree: V<Int?> = V(null)
    private val _multiEdgedness = V(EMultiEdgedness.DEFAULT)
    private val _name = if (isRoot) V("RootDirectedEdgeType") else V("NewDirectedEdgeType")
    private val _selfLooping = V(ESelfLooping.DEFAULT)
    private val _subTypeDirectedEdgeTypeInheritances = VLinkedList<DirectedEdgeTypeInheritance>()
    private val _superTypeDirectedEdgeTypeInheritances = VLinkedList<DirectedEdgeTypeInheritance>()
    private val _reverseName: V<String?> = V(null)
    private val _tailConnectivities = VLinkedList<DirectedEdgeTypeTailConnectivity>()
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
        get() = _headConnectivities.map { c -> c.connectedVertexType }.sortedBy { vt -> vt.name }

    /** The vertex type connected to the tail of this edge type. */
    val connectedTailVertexTypes: List<VertexType>
        get() = _tailConnectivities.map { c -> c.connectedVertexType }.sortedBy { vt -> vt.name }

    override var cyclicity: ECyclicity
        get() = _cyclicity.get()
        set(value) {

            check(!isRoot) {
                "Root directed edge type cyclicity cannot be changed"
            }

            _cyclicity.set(value)

        }

    override var description: String
        get() = _description.get()
        set(value) {


            check(!isRoot) {
                "Root directed edge type description cannot be changed"
            }

            _description.set(value)

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

    val headConnectivities: List<DirectedEdgeTypeHeadConnectivity>
        get() = _headConnectivities.sortedBy { c -> c.connectedVertexType.path }

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
                "Root directed edge type multiEdgedness cannot be changed"
            }

            _multiEdgedness.set(value)

        }

    override var name: String
        get() = _name.get()
        set(value) {

            check(!isRoot) {
                "Root directed edge type name cannot be changed"
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

    /** The sub types of this edge type. */
    val subTypeInheritances: List<DirectedEdgeTypeInheritance>
        get() = _subTypeDirectedEdgeTypeInheritances.sortedBy { et -> et.subType.path }

    /** The super types of this edge type. */
    val superTypes: List<DirectedEdgeType>
        get() = _superTypeDirectedEdgeTypeInheritances.map { i -> i.superType }.sortedBy { et -> et.path }

    /** The super types of this edge type. */
    val superTypeInheritances: List<DirectedEdgeTypeInheritance>
        get() = _superTypeDirectedEdgeTypeInheritances.sortedBy { et -> et.superType.path }

    val tailConnectivities: List<DirectedEdgeTypeTailConnectivity>
        get() = _tailConnectivities.sortedBy { c -> c.connectedVertexType.path }

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

        _headConnectivities.add(directedEdgeTypeHeadConnectivity)

    }

    /** Links a vertex type to this, its tail-connecting edge type's, list of vertex type connectivities. */
    internal fun addDirectedEdgeTypeTailConnectivity(
        directedEdgeTypeTailConnectivity: DirectedEdgeTypeTailConnectivity) {

        require(directedEdgeTypeTailConnectivity.connectingEdgeType === this) {
            "Cannot link a vertex type to a directed edge type not its tail connector."
        }

        _tailConnectivities.add(directedEdgeTypeTailConnectivity)

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
            "Cannot add a sub type to a directed edge type not its super type."
        }

        _subTypeDirectedEdgeTypeInheritances.add(directedEdgeTypeInheritance)

    }

    /** Adds a super directed edge type to this, its super type's, list of directed edge type inheritances. */
    internal fun addSuperTypeDirectedEdgeTypeInheritance(
        directedEdgeTypeInheritance: DirectedEdgeTypeInheritance) {

        require(directedEdgeTypeInheritance.subType === this) {
            "Cannot add a super type to a directed edge type not its sub type."
        }

        _superTypeDirectedEdgeTypeInheritances.add(directedEdgeTypeInheritance)

    }

    /** Finds the vertex types that are eligible to be connected by the head of this edge type. */
    fun findPotentialConnectedHeadVertexTypes(): List<VertexType> {

        val result = mutableListOf<VertexType>()

        val rootPackage = parents[0].findRootPackage()

        for (pkg in parents) {

            // same package as parent vertex type
            if ( !pkg.isRoot ) {

                for (vt in pkg.vertexTypes) {
                    result.add(vt)
                }

            }

            for (pkg2 in pkg.transitiveSuppliers) {

                for (vt in pkg2.vertexTypes) {
                    result.add(vt)
                }

            }

        }

        for (vt in rootPackage.vertexTypes) {
            result.add(vt)
        }

        return result

    }

    /** Finds the vertex types that are eligible to be connected by the tail of this edge type. */
    fun findPotentialConnectedTailVertexTypes(): List<VertexType> {

        val result = mutableListOf<VertexType>()

        val rootPackage = parents[0].findRootPackage()

        for (pkg in parents) {

            // same package as parent vertex type
            if ( !pkg.isRoot ) {

                for (vt in pkg.vertexTypes) {
                    result.add(vt)
                }

            }

            for (pkg2 in pkg.transitiveSuppliers) {

                for (vt in pkg2.vertexTypes) {
                    result.add(vt)
                }

            }

        }

        for (vt in rootPackage.vertexTypes) {
            result.add(vt)
        }

        return result

    }

    /** Finds the edge types that are eligible to be the super type of this edge type. */
    fun findPotentialSuperTypes(): List<DirectedEdgeType> {

        val result = mutableListOf<DirectedEdgeType>()

        val rootPackage = parents[0].findRootPackage()

        for (pkg in parents) {

            // same package as parent vertex type
            for (et in pkg.directedEdgeTypes) {
                if (et !== this && !et.hasSuperType(this)) {
                    result.add(et)
                }
            }

            for (pkg2 in pkg.transitiveSuppliers) {

                for (et in pkg2.directedEdgeTypes) {
                    if (!et.hasSuperType(this)) {
                        result.add(et)
                    }
                }

            }

            for (et in rootPackage.directedEdgeTypes) {
                result.add(et)
            }

        }

        return result

    }

    override fun hasParent(pkg: Package): Boolean {

        var result = false

        _directedEdgeTypeContainments.forEachWhile { edgeTypeContainment ->

            result = pkg === edgeTypeContainment.parent
            !result

        }

        return result

    }

    override fun hasParentPackage(pkg: Package) = hasParent(pkg)

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

    override fun remove() {
        _directedEdgeTypeContainments.forEach { c -> c.remove() }
        _headConnectivities.forEach { c -> c.remove() }
        _tailConnectivities.forEach { c -> c.remove() }
        _edgeAttributeTypeContainments.forEach { c -> c.remove() }
        _subTypeDirectedEdgeTypeInheritances.forEach { i -> i.remove() }
        _superTypeDirectedEdgeTypeInheritances.forEach { i -> i.remove() }
    }

    /** Unlinks a vertex type from this, its head-connecting edge type's, list of vertex type connectivities. */
    internal fun removeDirectedEdgeTypeHeadConnectivity(
        directedEdgeTypeHeadConnectivity: DirectedEdgeTypeHeadConnectivity) {

        require(_headConnectivities.remove(directedEdgeTypeHeadConnectivity)) {
            "Cannot unlink a vertex type from a directed edge type not its head connector."
        }

    }

    /** Unlinks a vertex type from this, its tail-connecting edge type's, list of vertex type connectivities. */
    internal fun removeDirectedEdgeTypeTailConnectivity(
        directedEdgeTypeTailConnectivity: DirectedEdgeTypeTailConnectivity) {

        require(_tailConnectivities.remove(directedEdgeTypeTailConnectivity)) {
            "Cannot unlink a vertex type from a directed edge type not its tail connector."
        }

    }

    /** Removes a package from this, its child edge type's, list of directed edge type containments. */
    internal fun removeDirectedEdgeTypeContainment(directedEdgeTypeContainment: DirectedEdgeTypeContainment) {

        require(_directedEdgeTypeContainments.remove(directedEdgeTypeContainment)) {
            "Cannot unlink a package from a directed edge type not its child."
        }

    }

    /** Removes an attribute type from this, its parent edge type's, list of edge attribute type containments. */
    override fun removeEdgeAttributeTypeContainment(edgeAttributeTypeContainment: EdgeAttributeTypeContainment) {

        require(_edgeAttributeTypeContainments.remove(edgeAttributeTypeContainment)) {
            "Cannot unlink an edge attribute type from an edge type not its container."
        }

    }

    /** Removes a sub directed edge type from this, its super type's, list of directed edge type inheritances. */
    internal fun removeSubTypeDirectedEdgeTypeInheritance(directedEdgeTypeInheritance: DirectedEdgeTypeInheritance) {

        require(_subTypeDirectedEdgeTypeInheritances.remove(directedEdgeTypeInheritance)) {
            "Cannot remove a sub type from a directed edge type not its super type."
        }

    }

    /** Removes a super directed edge type from this, its super type's, list of directed edge type inheritances. */
    internal fun removeSuperTypeDirectedEdgeTypeInheritance(
        directedEdgeTypeInheritance: DirectedEdgeTypeInheritance) {

        require(_superTypeDirectedEdgeTypeInheritances.remove(directedEdgeTypeInheritance)) {
            "Cannot remove a super type from a directed edge type not its sub type."
        }

    }

}
