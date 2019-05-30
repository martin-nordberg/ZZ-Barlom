//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.propertygraphs

import o.barlom.infrastructure.graphs.IUndirectedConnection

//---------------------------------------------------------------------------------------------------------------------

/**
 * An undirected connection (edge or link) in a graph with properties that can be retrieved by name.
 */
interface IUndirectedPropertyConnection<Connection, Concept : IPropertyConcept<Concept>>
    : IUndirectedConnection<Connection, Concept>, IPropertyContainer

//---------------------------------------------------------------------------------------------------------------------
