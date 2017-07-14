//
// (C) Copyright 2014-2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.elements;

import org.barlom.domain.metamodel.types.EAbstractness
import org.barlom.domain.metamodel.types.ECyclicity
import org.barlom.domain.metamodel.types.EMultiEdgedness
import org.barlom.domain.metamodel.types.ESelfLooping

/**
 * Implementation class for non-root edge types.
 */
abstract class NamedEdgeType(

    id: String,
    name: String,

    /** The parent package of this edge type. */
    override final val parentPackage: NamedPackage,

    abstractness : EAbstractness,
    cyclicity : ECyclicity,
    multiEdgedness : EMultiEdgedness,
    selfLooping : ESelfLooping

) : EdgeType( id, name, abstractness, cyclicity, multiEdgedness, selfLooping ) {

    internal fun addAttribute( attribute: EdgeAttributeDecl ) {
        _attributes.add( attribute );
    }

    override val attributes : List<EdgeAttributeDecl>
        get() = _attributes

    /** The attribute declarations within this edge type. */
    private val _attributes : MutableList<EdgeAttributeDecl> = mutableListOf()

}
