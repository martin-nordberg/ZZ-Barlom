//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.propertygraphs

import o.barlom.infrastructure.graphs.IDirectedConnection

//---------------------------------------------------------------------------------------------------------------------

/**
 * A directed connection (edge or link) in a graph with properties that can be retrieved by name.
 */
interface IDirectedPropertyConnection<
    Connection: IDirectedPropertyConnection<Connection, FromConcept, ToConcept>,
    FromConcept : IPropertyConcept<FromConcept>,
    ToConcept : IPropertyConcept<ToConcept>
> : IDirectedConnection<Connection, FromConcept, ToConcept>, IPropertyContainer

//---------------------------------------------------------------------------------------------------------------------
