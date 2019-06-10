//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.graphschema.api.concepts

//---------------------------------------------------------------------------------------------------------------------

/**
 * Interface to a concept that is contained in a package.
 */
abstract class AbstractPackagedConcept<Concept : AbstractPackagedConcept<Concept>> internal constructor()
    : AbstractNamedConcept<Concept>()

//---------------------------------------------------------------------------------------------------------------------

