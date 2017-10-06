//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.actions

import org.barlom.domain.metamodel.api.model.Model

/**
 * Function representing an action taken to update a model.
 */
typealias ModelAction = (model: Model) -> String
