//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.graphs

//---------------------------------------------------------------------------------------------------------------------

/**
 * A concept (vertex or node) in a property graph. Provides the unique ID of the concept.
 */
interface IConcept<Concept> {

    /** The name of the concrete concept type Concept. */
    val conceptTypeName: String

    /** The unique ID of the concept. */
    val id: Id<Concept>

}

//---------------------------------------------------------------------------------------------------------------------
