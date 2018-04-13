//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.org.barlom.domain.metamodel.api.vertices

import o.org.barlom.domain.metamodel.api.edges.*
import o.org.barlom.domain.metamodel.api.types.EAbstractness
import o.org.barlom.infrastructure.revisions.V
import o.org.barlom.infrastructure.revisions.VLinkedList
import x.org.barlom.infrastructure.uuids.Uuid

class VertexType(

    override val id: Uuid,
    val isRoot: Boolean

) : AbstractPackagedElement() {

    private val _abstractness = if (isRoot) V(EAbstractness.ABSTRACT) else V(EAbstractness.CONCRETE)
    private val _description = if (isRoot) V("Root vertex type.") else V("")
    private val _directedEdgeTypeHeadConnectivities = VLinkedList<DirectedEdgeTypeHeadConnectivity>()
    private val _directedEdgeTypeTailConnectivities = VLinkedList<DirectedEdgeTypeTailConnectivity>()
    private val _name = if (isRoot) V("RootVertexType") else V("NewVertexType")
    private val _subTypeVertexTypeInheritances = VLinkedList<VertexTypeInheritance>()
    private val _superTypeVertexTypeInheritances = VLinkedList<VertexTypeInheritance>()
    private val _undirectedEdgeTypeConnectivities = VLinkedList<UndirectedEdgeTypeConnectivity>()
    private val _vertexTypeContainments = VLinkedList<VertexTypeContainment>()
    private val _vertexAttributeTypeContainments = VLinkedList<VertexAttributeTypeContainment>()


    /** Whether this vertex type is abstract. */
    var abstractness: EAbstractness
        get() = _abstractness.get()
        set(value) {

            check(!isRoot) {
                "Root vertex type abstractness cannot be changed"
            }

            _abstractness.set(value)

        }

    /** The vertex attributes types within this vertex type. */
    val attributeTypes: List<VertexAttributeType>
        get() = _vertexAttributeTypeContainments.map { c -> c.attributeType }.sortedBy { at -> at.name }

    /** The undirected edge types connecting this vertex type. */
    val connectingEdgeTypes: List<UndirectedEdgeType>
        get() = _undirectedEdgeTypeConnectivities.map { c -> c.connectingEdgeType }.sortedBy { et -> et.name }

    /** The directed edge types connecting their head to this vertex type. */
    val connectingHeadEdgeTypes: List<DirectedEdgeType>
        get() = _directedEdgeTypeHeadConnectivities.map { c -> c.connectingEdgeType }.sortedBy { et -> et.name }

    /** The directed edge types connecting their tail to this vertex type. */
    val connectingTailEdgeTypes: List<DirectedEdgeType>
        get() = _directedEdgeTypeTailConnectivities.map { c -> c.connectingEdgeType }.sortedBy { et -> et.name }

    override var description: String
        get() = _description.get()
        set(value) {


            check(!isRoot) {
                "Root vertex type description cannot be changed"
            }

            _description.set(value)

        }

    override var name: String
        get() = _name.get()
        set(value) {

            check(!isRoot) {
                "Root vertex type name cannot be changed"
            }

            _name.set(value)

        }

    override val parents: List<Package>
        get() = _vertexTypeContainments.map { c -> c.parent }.sortedBy { pkg -> pkg.name }

    override val path: String
        get() {

            if (_vertexTypeContainments.isEmpty()) {
                return name
            }

            val parentPath = parents[0].path

            if (parentPath.isEmpty()) {
                return name
            }

            return parentPath + "." + name

        }

    /** The sub types of this vertex type. */
    val subTypes: List<VertexType>
        get() = _subTypeVertexTypeInheritances.map { i -> i.subType }.sortedBy { vt -> vt.path }

    /** Link to the package containing this vertex type. */
    val subTypeVertexTypeInheritances: List<VertexTypeInheritance>
        get() = _subTypeVertexTypeInheritances.sortedBy { i -> i.subType.path }

    /** The super types of this vertex type. */
    val superTypes: List<VertexType>
        get() = _superTypeVertexTypeInheritances.map { i -> i.superType }.sortedBy { vt -> vt.path }

    /** Link to the package containing this vertex type. */
    val superTypeVertexTypeInheritances: List<VertexTypeInheritance>
        get() = _superTypeVertexTypeInheritances.sortedBy { i -> i.superType.path }

    /** Link to the package containing this vertex type. */
    val vertexTypeContainments: List<VertexTypeContainment>
        get() = _vertexTypeContainments.sortedBy { c -> c.parent.name }

    /** Links to attributes of this vertex type. */
    val vertexAttributeTypeContainments: List<VertexAttributeTypeContainment>
        get() = _vertexAttributeTypeContainments.sortedBy { c -> c.attributeType.name }


    /** Links a vertex type to this, its head-connecting edge type's, list of vertex type connectivities. */
    internal fun addDirectedEdgeTypeHeadConnectivity(
        directedEdgeTypeHeadConnectivity: DirectedEdgeTypeHeadConnectivity) {

        require(directedEdgeTypeHeadConnectivity.connectedVertexType === this) {
            "Cannot link an edge type to a vertext type not its head connector."
        }

        _directedEdgeTypeHeadConnectivities.add(directedEdgeTypeHeadConnectivity)

    }

    /** Links a vertex type to this, its tail-connecting edge type's, list of vertex type connectivities. */
    internal fun addDirectedEdgeTypeTailConnectivity(
        directedEdgeTypeTailConnectivity: DirectedEdgeTypeTailConnectivity) {

        require(directedEdgeTypeTailConnectivity.connectedVertexType === this) {
            "Cannot link an edge type to a vertext type not its tail connector."
        }

        _directedEdgeTypeTailConnectivities.add(directedEdgeTypeTailConnectivity)

    }

    /** Adds a sub vertex type to this, its super type's, list of vertex type inheritances. */
    internal fun addSubTypeVertexTypeInheritance(vertexTypeInheritance: VertexTypeInheritance) {

        require(vertexTypeInheritance.superType === this) {
            "Cannot add a sub type to a vertex type not its super type."
        }

        _subTypeVertexTypeInheritances.add(vertexTypeInheritance)

    }

    /** Adds a super vertex type to this, its super type's, list of vertex type inheritances. */
    internal fun addSuperTypeVertexTypeInheritance(vertexTypeInheritance: VertexTypeInheritance) {

        require(vertexTypeInheritance.subType === this) {
            "Cannot add a super type to a vertex type not its sub type."
        }

        _superTypeVertexTypeInheritances.add(vertexTypeInheritance)

    }

    /** Adds an undirected edge type to this, its connected vertex type's, list of undirected edge type connectivities. */
    internal fun addUndirectedEdgeTypeConnectivity(undirectedEdgeTypeConnectivity: UndirectedEdgeTypeConnectivity) {

        require(undirectedEdgeTypeConnectivity.connectedVertexType === this) {
            "Cannot link an undirected edge type to an vertex type it does not connect."
        }

        _undirectedEdgeTypeConnectivities.add(undirectedEdgeTypeConnectivity)

    }

    /** Adds a vertex attribute type to this, its parent vertex type's, list of vertex attribute type containments. */
    internal fun addVertexAttributeTypeContainment(vertexAttributeTypeContainment: VertexAttributeTypeContainment) {

        require(vertexAttributeTypeContainment.vertexType === this) {
            "Cannot add a vertex type to a package not its parent."
        }

        _vertexAttributeTypeContainments.add(vertexAttributeTypeContainment)

    }

    /** Adds a vertex type to this, its parent package's, list of vertex type containments. */
    internal fun addVertexTypeContainment(vertexTypeContainment: VertexTypeContainment) {

        require(vertexTypeContainment.child === this) {
            "Cannot add a package to a vertex type not its child."
        }

        _vertexTypeContainments.add(vertexTypeContainment)

    }

    /** Finds the vertex types that are eligible to be the super type of this vertex type. */
    fun findPotentialSuperTypes(): List<VertexType> {

        val result = mutableListOf<VertexType>()

        val rootPackage = parents[0].findRootPackage()

        for (pkg in parents) {

            // same package as parent vertex type
            for (vt in pkg.vertexTypes) {
                if (vt !== this && !vt.hasSuperType(this)) {
                    result.add(vt)
                }
            }

            for (pkg2 in pkg.transitiveSuppliers) {

                for (vt in pkg2.vertexTypes) {
                    if (!vt.hasSuperType(this)) {
                        result.add(vt)
                    }
                }

            }

            for (vt in rootPackage.vertexTypes) {
                result.add(vt)
            }

        }

        return result

    }

    override fun hasParent(pkg: Package): Boolean {

        var result = false

        _vertexTypeContainments.forEachWhile { vertexTypeContainment ->

            result = pkg === vertexTypeContainment.parent
            !result

        }

        return result

    }

    override fun hasParentPackage(pkg: Package) = hasParent(pkg)

    /** Whether the given [vertexType] is a direct sub type of this one. */
    fun hasSubType(vertexType: VertexType): Boolean {

        var result = false

        _subTypeVertexTypeInheritances.forEachWhile { vertexTypeInheritance ->

            result = vertexType === vertexTypeInheritance.subType
            !result

        }

        return result

    }

    /** Whether the given [vertexType] is a direct super type of this one. */
    fun hasSuperType(vertexType: VertexType): Boolean {

        var result = false

        _superTypeVertexTypeInheritances.forEachWhile { vertexTypeInheritance ->

            result = vertexType === vertexTypeInheritance.superType
            !result

        }

        return result

    }

    /** Whether the given [vertexType] is a direct or indirect sub type of this one. */
    fun hasTransitiveSubType(vertexType: VertexType): Boolean {

        val subTypes: MutableSet<VertexType> = mutableSetOf()

        // Helper function recursively searches while accumulating the vertex types searched so far
        fun findSubType(superType: VertexType): Boolean {

            for (vertexTypeInheritance in superType._subTypeVertexTypeInheritances.asList()) {

                val subType = vertexTypeInheritance.subType

                if (subType === vertexType) {
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

    /** Whether the given [vertexType] is a direct or indirect super type of this one. */
    fun hasTransitiveSuperType(vertexType: VertexType): Boolean {

        val superTypes: MutableSet<VertexType> = mutableSetOf()

        // Helper function recursively searches while accumulating the vertex types searched so far
        fun findSuperType(subType: VertexType): Boolean {

            for (vertexTypeInheritance in subType._superTypeVertexTypeInheritances.asList()) {

                val superType = vertexTypeInheritance.superType

                if (superType === vertexType) {
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
        _directedEdgeTypeHeadConnectivities.forEach { c -> c.remove() }
        _directedEdgeTypeTailConnectivities.forEach { c -> c.remove() }
        _subTypeVertexTypeInheritances.forEach { i -> i.remove() }
        _superTypeVertexTypeInheritances.forEach { i -> i.remove() }
        _undirectedEdgeTypeConnectivities.forEach { c -> c.remove() }
        _vertexAttributeTypeContainments.forEach { c -> c.remove() }
        _vertexTypeContainments.forEach { c -> c.remove() }
    }

    /** Unlinks a vertex type from this, its head-connecting edge type's, list of vertex type connectivities. */
    internal fun removeDirectedEdgeTypeHeadConnectivity(
        directedEdgeTypeHeadConnectivity: DirectedEdgeTypeHeadConnectivity) {

        require(_directedEdgeTypeHeadConnectivities.remove(directedEdgeTypeHeadConnectivity)) {
            "Cannot unlink an edge type from a vertex type not its head connector."
        }

    }

    /** Unlinks a vertex type from this, its tail-connecting edge type's, list of vertex type connectivities. */
    internal fun removeDirectedEdgeTypeTailConnectivity(
        directedEdgeTypeTailConnectivity: DirectedEdgeTypeTailConnectivity) {

        require(_directedEdgeTypeTailConnectivities.remove(directedEdgeTypeTailConnectivity)) {
            "Cannot unlink an edge type from a vertex type not its tail connector."
        }

    }

    /** Removes a sub vertex type from this, its super type's, list of vertex type inheritances. */
    internal fun removeSubTypeVertexTypeInheritance(vertexTypeInheritance: VertexTypeInheritance) {

        require(_subTypeVertexTypeInheritances.remove(vertexTypeInheritance)) {
            "Cannot remove a sub type from a vertex type not its super type."
        }

    }

    /** Removes a super vertex type from this, its super type's, list of vertex type inheritances. */
    internal fun removeSuperTypeVertexTypeInheritance(vertexTypeInheritance: VertexTypeInheritance) {

        require(_superTypeVertexTypeInheritances.remove(vertexTypeInheritance)) {
            "Cannot remove a super type from a vertex type not its sub type."
        }

    }

    /** Removes an undirected edge type to this, its connected vertex type's, list of undirected edge type connectivities. */
    internal fun removeUndirectedEdgeTypeConnectivity(undirectedEdgeTypeConnectivity: UndirectedEdgeTypeConnectivity) {

        require(_undirectedEdgeTypeConnectivities.remove(undirectedEdgeTypeConnectivity)) {
            "Cannot unlink an undirected edge type from an vertex type it does not connect."
        }

    }

    /** Removes a vertex attribute type from this, its parent vertex type's, list of vertex attribute type containments. */
    internal fun removeVertexAttributeTypeContainment(vertexAttributeTypeContainment: VertexAttributeTypeContainment) {

        require(_vertexAttributeTypeContainments.remove(vertexAttributeTypeContainment)) {
            "Cannot remove a vertex type from a package not its parent."
        }

    }

    /** Removes a vertex type from this, its parent package's, list of vertex type containments. */
    internal fun removeVertexTypeContainment(vertexTypeContainment: VertexTypeContainment) {

        require(_vertexTypeContainments.remove(vertexTypeContainment)) {
            "Cannot remove a package from a vertex type not its child."
        }

    }

}
