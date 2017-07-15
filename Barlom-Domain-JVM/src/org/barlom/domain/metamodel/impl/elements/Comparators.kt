//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements;

import org.barlom.domain.metamodel.api.elements.*


/**
 * Comparator to sort directed edge types by inheritance then name.
 */
fun byDirectedEdgeSuperSubType(e1: IDirectedEdgeType, e2: IDirectedEdgeType): Int {

    if (e1 == e2) {
        return 0
    }

    if (e1.isSubTypeOf(e2)) {
        return 1
    }

    if (e2.isSubTypeOf(e1)) {
        return -1
    }

    return byName(e1, e2)

}

/**
 * Comparator for sorting elements by name.
 */
fun byName(n1: INamedElement, n2: INamedElement): Int {
    return n1.name.compareTo(n2.name);
}

/**
 * Comparator for sorting packages by parent/child.
 */
fun byPackageParentChild(p1: IPackage, p2: IPackage): Int {

    if (p1.isChildOf(p2)) {
        return 1
    }

    if (p2.isChildOf(p1)) {
        return -1
    }

    return byName(p1, p2)

}

/**
 * Comparator for sorting elements by path.
 */
fun byPath(p1: IPackage, p2: IPackage): Int {
    return p1.path.compareTo(p2.path)
}

/**
 * Comparator for sorting undirected edge types by their super/sub type hierarchy.
 */
fun byUndirectedEdgeSuperSubType(e1: IUndirectedEdgeType, e2: IUndirectedEdgeType): Int {

    if (e1 == e2) {
        return 0
    }

    if (e1.isSubTypeOf(e2)) {
        return 1
    }

    if (e2.isSubTypeOf(e1)) {
        return -1
    }

    return byName(e1, e2)

}

/**
 * Comparator for sorting vertex types by their super/sub type hierarchy.
 */
fun byVertexSuperSubType(v1: IVertexType, v2: IVertexType): Int {

    if (v1 == v2) {
        return 0;
    }

    if (v1.isSubTypeOf(v2)) {
        return 1;
    }

    if (v2.isSubTypeOf(v1)) {
        return -1;
    }

    return byName(v1, v2)

}

