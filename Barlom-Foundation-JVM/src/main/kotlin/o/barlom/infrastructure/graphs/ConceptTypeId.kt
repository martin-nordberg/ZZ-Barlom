//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.graphs

//---------------------------------------------------------------------------------------------------------------------

/**
 * The type of a concept.
 */
@Suppress("unused")
data class ConceptTypeId<Concept : IConcept<Concept>>(

    /** The name of this concept type. */
    val typeName: String

)

//---------------------------------------------------------------------------------------------------------------------
