//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.actions

import org.barlom.domain.metamodel.api.model.Model
import org.barlom.domain.metamodel.api.vertices.AbstractNamedElement

/**
 * Renames the given [element] to [newName].
 */
fun rename(element: AbstractNamedElement, newName: String): ModelAction {

    return { model: Model ->
        val oldPath = element.path
        element.name = newName
        "Rename ${oldPath} to ${newName}."
    }

}
