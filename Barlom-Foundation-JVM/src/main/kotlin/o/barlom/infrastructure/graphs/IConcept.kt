//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.graphs

//---------------------------------------------------------------------------------------------------------------------

/**
 * A concept (vertex or node) in a graph. Provides the unique ID of the concept.
 */
interface IConcept<Concept>
    : Id<Concept> {

    /** The unique ID of the concept. */
    val id: Id<Concept>
        get() = this

}

//---------------------------------------------------------------------------------------------------------------------
