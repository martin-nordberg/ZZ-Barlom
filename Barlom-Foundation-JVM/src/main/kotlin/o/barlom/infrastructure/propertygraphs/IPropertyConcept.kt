//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.propertygraphs

import o.barlom.infrastructure.graphs.IConcept

//---------------------------------------------------------------------------------------------------------------------

/**
 * A concept (vertex or node) in a graph with properties retrievable by name.
 */
interface IPropertyConcept<Concept: IPropertyConcept<Concept>> : IConcept<Concept>, IPropertyContainer

//---------------------------------------------------------------------------------------------------------------------

