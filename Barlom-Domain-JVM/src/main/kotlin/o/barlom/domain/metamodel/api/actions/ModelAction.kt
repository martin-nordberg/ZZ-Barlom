//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.domain.metamodel.api.actions

import o.barlom.domain.metamodel.api.model.Model

/**
 * Function representing an action taken to update a model.
 */
typealias ModelAction = (model: Model) -> String
